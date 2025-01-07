package com.nikasov.data.local.mapper

import com.nikasov.data.local.entity.HomeEntity
import com.nikasov.domain.entity.Home

fun HomeEntity.toHome(isFavorite: Boolean): Home {
    return Home(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        location = location,
        isFavorite = isFavorite
    )
}

fun Home.toHomeEntity(): HomeEntity {
    return HomeEntity(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
        location = location,
    )
}