package com.example.myportfolio.di

import com.example.myportfolio.data.datasource.AssetsDataSource
import com.example.myportfolio.data.datasource.AssetsSampleSource
import com.example.myportfolio.data.repository.AssetRepositoryImpl
import com.example.myportfolio.data.repository.PreferencesDataStoreRepository
import com.example.myportfolio.domain.repository.AssetRepository
import com.example.myportfolio.domain.repository.SettingsRepository
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
    abstract fun bindAssetRepository(
        repository: AssetRepositoryImpl
    ): AssetRepository

    @Binds
    @Singleton
    abstract fun bindDataSource(
        dataSource: AssetsSampleSource
    ): AssetsDataSource

    @Binds
    @Singleton
    abstract fun bindSettingsStore(
        settingsRepository: PreferencesDataStoreRepository
    ): SettingsRepository
}
