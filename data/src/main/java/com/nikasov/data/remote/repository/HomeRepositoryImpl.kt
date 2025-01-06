package com.nikasov.data.remote.repository

import android.util.Log
import com.nikasov.data.local.dao.HomeDao
import com.nikasov.data.remote.api.NetworkApi
import com.nikasov.data.remote.mapper.toPost
import com.nikasov.domain.entity.Home
import com.nikasov.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi,
    private val homeDao: HomeDao
) : HomeRepository {
    override suspend fun getHomes(start: Int, pageSize: Int): Result<List<Home>> {
        return try {
            delay(3000)
            return networkApi.getHomes(start, pageSize).let { data ->
                Log.i(HomeRepositoryImpl::class.simpleName, "--- Load homes:\nstart: $start\n---")
                val homes = data.map { postDto -> postDto.toPost() }
                Log.i(HomeRepositoryImpl::class.simpleName, "--- Homes:\nsize: ${homes.size}, last: ${homes.lastOrNull()?.id}\n---")
                Result.success(homes)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun clear() {
        homeDao.clearHomes()
    }

    override suspend fun toggleFavorite(homeId: Int) {
        homeDao.toggleFavorite(homeId)
    }

}