package com.nikasov.data.remote.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HomeDto(
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "name")
    val name: String? = null,
    @field:Json(name = "price")
    val price: Int? = null,
    @field:Json(name = "imageUrl")
    val imageUrl: String? = null,
    @field:Json(name = "location")
    val location: String? = null,
)
