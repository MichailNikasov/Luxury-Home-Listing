package com.nikasov.data.remote.api

import com.nikasov.data.remote.entity.HomeDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkApi {
    @GET("homes")
    suspend fun getHomes(
        @Query("start") start: Int,
        @Query("pageSize") pageSize: Int
    ): List<HomeDto>
}