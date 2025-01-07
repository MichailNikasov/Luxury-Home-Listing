package com.nikasov.pacasotestproject.ui.screen.home

sealed class HomeScreenEvent {
    data class ToggleFavorite(val homeId: Int) : HomeScreenEvent()
    data object ToggleShowError : HomeScreenEvent()
}