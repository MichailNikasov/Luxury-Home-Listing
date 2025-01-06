package com.nikasov.domain.repository

import com.nikasov.domain.entity.Home

interface HomeRepository {
    suspend fun getHomes(start: Int, pageSize: Int): Result<List<Home>>
    suspend fun clear()
    suspend fun toggleFavorite(homeId: Int)
}