package com.nikasov.di.modules

import com.nikasov.data.manager.AppSettingsImpl
import com.nikasov.domain.manager.AppSettings
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerBindsModule {
    @Binds
    @Singleton
    abstract fun bindAppSettings(appSettings: AppSettingsImpl): AppSettings
}