package com.nikasov.di.modules

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.nikasov.data.local.dao.FavoriteDao
import com.nikasov.data.local.dao.HomeDao
import com.nikasov.data.local.database.HomeDatabase
import com.nikasov.data.local.entity.HomeWithFavorite
import com.nikasov.data.mediators.HomeMediator
import com.nikasov.domain.usecase.GetHomesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HomeDatabase {
        return Room.databaseBuilder(context, HomeDatabase::class.java, "post_database").build()
    }

    @Provides
    @Singleton
    fun provideHomeDao(homeDatabase: HomeDatabase): HomeDao = homeDatabase.homeDao()

    @Provides
    @Singleton
    fun provideFavoriteDao(homeDatabase: HomeDatabase): FavoriteDao = homeDatabase.favoriteDao()

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideHomePager(database: HomeDatabase, useCase: GetHomesUseCase): Pager<Int, HomeWithFavorite> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 20
            ),
            remoteMediator = HomeMediator(useCase, database),
            pagingSourceFactory = { database.homeDao().getHomesWithFavorites() },
        )
    }

}