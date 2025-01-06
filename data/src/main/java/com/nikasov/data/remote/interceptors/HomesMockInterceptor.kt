package com.nikasov.data.remote.interceptors

import com.nikasov.data.remote.entity.HomeDto
import com.nikasov.domain.manager.AppSettings
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject
import kotlin.math.roundToInt
import kotlin.random.Random

class HomesMockInterceptor @Inject constructor(
    private val appSettings: AppSettings
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url()

        if (url.encodedPath() != "/homes") {
            throw IllegalArgumentException("Unknown endpoint")
        }

        val start = url.queryParameter("start")?.toIntOrNull() ?: 1
        val pageSize = url.queryParameter("pageSize")?.toIntOrNull() ?: 10

        val firstIndex = if (start == 1) 1 else start + 1

        val items = (firstIndex until (firstIndex + pageSize)).mapNotNull { id ->
            if (id >= 60) {
                null
            } else {
                HomeDto(
                    id = id,
                    name = homeNames[id],
                    price = (1_000_000 * Random.nextDouble(5.0)).roundToInt(),
                    imageUrl = images[id],
                    location = locations[id],
                )
            }
        }

        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, HomeDto::class.java)
        val adapter: JsonAdapter<List<HomeDto>> = moshi.adapter(type)
        val json = adapter.toJson(items)

        /**
         *  {
         *     "id": 1,
         *     "name": "Seaside Villa",
         *     "price": 2500000,
         *     "imageUrl": "https://example.com/villa.jpg",
         *     "location": "Malibu, CA"
         *   },
         */
        val body = ResponseBody.create(MediaType.parse("application/json"), json)

        if (runBlocking { appSettings.isShowError.first() }) {
            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(500)
                .message("Simulated Server Error")
                .body(ResponseBody.create(MediaType.parse("text/plain"), "{errorMessage: \"Internal error\"}"))
                .build()
        } else {
            return Response.Builder()
                .code(200)
                .message("Mock response")
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .body(body)
                .build()
        }
    }
}
