package com.nikasov.pacasotestproject.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.nikasov.data.local.entity.HomeEntity
import com.nikasov.data.local.mapper.toHome
import com.nikasov.domain.manager.AppSettings
import com.nikasov.domain.usecase.ClearHomeListUseCase
import com.nikasov.domain.usecase.ToggleFavoriteHomeUseCase
import com.nikasov.pacasotestproject.ui.widget.mapper.toHomeItemData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    homePager: Pager<Int, HomeEntity>,
    private val toggleFavoriteHomeUseCase: ToggleFavoriteHomeUseCase,
    private val appSettings: AppSettings,
    private val clearHomeListUseCase: ClearHomeListUseCase
) : ViewModel() {

    val homeItems = homePager
        .flow
        .map { it.map { homeEntity -> homeEntity.toHome().toHomeItemData() } }
        .cachedIn(viewModelScope)

    val showError = appSettings.isShowError.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleFavorite(homeId: Int) {
        viewModelScope.launch {
            toggleFavoriteHomeUseCase(homeId)
        }
    }

    fun toggleShowError() {
        viewModelScope.launch {
            appSettings.toggleShowError()
        }
    }

    fun clearList() {
        viewModelScope.launch {
            clearHomeListUseCase()
        }
    }

}