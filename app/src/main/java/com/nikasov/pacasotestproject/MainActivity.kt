package com.nikasov.pacasotestproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.nikasov.pacasotestproject.ui.screen.home.HomeScreen
import com.nikasov.pacasotestproject.ui.screen.home.HomeScreenEvent
import com.nikasov.pacasotestproject.ui.screen.home.HomeViewModel
import com.nikasov.pacasotestproject.ui.theme.PacasoTestProjectTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PacasoTestProjectTheme {
                val homeViewModel = hiltViewModel<HomeViewModel>()
                val homes = homeViewModel.homeItems.collectAsLazyPagingItems()
                val showError by homeViewModel.showError.collectAsState(initial = false)
                HomeScreen(
                    homes = homes,
                    showError = showError,
                    eventHandler = { event ->
                        when (event) {
                            is HomeScreenEvent.ToggleFavorite -> homeViewModel.toggleFavorite(event.homeId)
                            HomeScreenEvent.ToggleShowError -> homeViewModel.toggleShowError()
                            HomeScreenEvent.ClearList -> homeViewModel.clearList()
                        }
                    }
                )
            }
        }
    }
}
