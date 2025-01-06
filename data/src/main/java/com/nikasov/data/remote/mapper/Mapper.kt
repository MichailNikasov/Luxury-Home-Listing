package com.nikasov.data.remote.mapper

import com.nikasov.data.remote.entity.HomeDto
import com.nikasov.domain.entity.Home

fun HomeDto.toPost(): Home {
    return Home(
        id = id ?: 0,
        name = name.orEmpty(),
        price = price ?: 0,
        imageUrl = imageUrl.toString(),
        location = location.toString(),
        isFavorite = false
    )
}