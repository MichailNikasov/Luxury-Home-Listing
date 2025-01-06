package com.nikasov.di.modules

import com.nikasov.data.remote.repository.HomeRepositoryImpl
import com.nikasov.domain.repository.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindRepository(postRepositoryImpl: HomeRepositoryImpl): HomeRepository
}