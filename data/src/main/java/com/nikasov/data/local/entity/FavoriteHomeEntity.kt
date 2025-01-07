package com.nikasov.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_homes")
data class FavoriteHomeEntity(
    @PrimaryKey
    val id: Int
)