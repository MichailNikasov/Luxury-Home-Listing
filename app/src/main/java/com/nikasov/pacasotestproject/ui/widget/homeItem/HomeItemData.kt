package com.nikasov.pacasotestproject.ui.widget.homeItem

import androidx.compose.runtime.Stable

@Stable
class HomeItemData(
    val id: Int,
    val name: String,
    val price: String,
    val imageUrl: String,
    val location: String,
    val isFavorite: Boolean
)