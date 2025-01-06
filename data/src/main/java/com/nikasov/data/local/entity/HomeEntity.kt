package com.nikasov.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "homes")
data class HomeEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Int,
    val imageUrl: String,
    val location: String,
    val isFavorite: Boolean
)
