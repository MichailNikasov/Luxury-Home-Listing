package com.nikasov.domain.entity

data class Home(
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val location: String,
    val isFavorite: Boolean
)
