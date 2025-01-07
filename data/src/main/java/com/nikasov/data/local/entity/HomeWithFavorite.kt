package com.nikasov.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class HomeWithFavorite(
    @Embedded val home: HomeEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val favorite: FavoriteHomeEntity?
)