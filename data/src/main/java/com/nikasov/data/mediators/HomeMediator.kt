package com.nikasov.data.mediators

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nikasov.data.local.database.HomeDatabase
import com.nikasov.data.local.entity.HomeWithFavorite
import com.nikasov.data.local.mapper.toHomeEntity
import com.nikasov.domain.usecase.GetHomesUseCase
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class HomeMediator(
    private val getHomesUseCase: GetHomesUseCase,
    private val homeDatabase: HomeDatabase,
) : RemoteMediator<Int, HomeWithFavorite>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HomeWithFavorite>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    Log.i(HomeMediator::class.simpleName, "REFRESH")
                    1
                }

                LoadType.PREPEND -> {
                    Log.i(HomeMediator::class.simpleName, "PREPEND")
                    return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    val lastPage = state.lastItemOrNull()
                    lastPage?.home?.id ?: 1
                }
            }
            return fetchHomes(loadKey, state.config.pageSize, loadType)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun fetchHomes(loadKey: Int, pageSize: Int, loadType: LoadType): MediatorResult {
        val homes = getHomesUseCase(
            start = loadKey,
            pageSize = pageSize
        ).getOrThrow()
        homeDatabase.withTransaction {
            if (loadType == LoadType.REFRESH) {
                homeDatabase.homeDao().clearHomes()
            }
            val homeEntityList = homes.map { it.toHomeEntity() }
            homeDatabase.homeDao().upsertHomes(homeEntityList)
        }
        return MediatorResult.Success(endOfPaginationReached = homes.size < pageSize)
    }

}