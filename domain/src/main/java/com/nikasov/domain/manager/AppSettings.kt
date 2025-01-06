package com.nikasov.domain.manager

import kotlinx.coroutines.flow.Flow

interface AppSettings {
    val isShowError: Flow<Boolean>
    suspend fun toggleShowError()
}