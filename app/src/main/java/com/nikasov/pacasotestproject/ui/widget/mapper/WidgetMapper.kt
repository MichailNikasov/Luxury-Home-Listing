package com.nikasov.pacasotestproject.ui.widget.mapper

import com.nikasov.domain.entity.Home
import com.nikasov.pacasotestproject.ui.widget.homeItem.HomeItemData

fun Int.convertToPrice() = this
    .toString()
    .reversed()
    .chunked(3)
    .joinToString(",")
    .plus("$")
    .reversed()

fun Home.toHomeItemData() = HomeItemData(
    id = id,
    name = name,
    price = price.convertToPrice(),
    imageUrl = imageUrl,
    location = location,
    isFavorite = isFavorite
)
