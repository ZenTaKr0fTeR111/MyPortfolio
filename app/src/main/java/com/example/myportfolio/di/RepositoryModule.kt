package com.example.myportfolio.di

import com.example.myportfolio.data.datasource.assets.AssetsDataSource
import com.example.myportfolio.data.datasource.assets.AssetsSampleSource
import com.example.myportfolio.data.datasource.conversion_rate.local.ConversionRateDbSource
import com.example.myportfolio.data.datasource.conversion_rate.local.ConversionRateLocalDataSource
import com.example.myportfolio.data.datasource.conversion_rate.remote.ConversionRateApiDataSource
import com.example.myportfolio.data.datasource.conversion_rate.remote.ConversionRateRemoteDataSource
import com.example.myportfolio.data.repository.AssetRepositoryImpl
import com.example.myportfolio.data.repository.ConversionRateRepositoryImpl
import com.example.myportfolio.data.repository.PreferencesDataStoreRepository
import com.example.myportfolio.domain.repository.AssetRepository
import com.example.myportfolio.domain.repository.ConversionRateRepository
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
    abstract fun bindAssetDataSource(
        dataSource: AssetsSampleSource
    ): AssetsDataSource

    @Binds
    @Singleton
    abstract fun bindConversionRateLocalDataSource(
        dataSource: ConversionRateDbSource
    ): ConversionRateLocalDataSource

    @Binds
    @Singleton
    abstract fun bindConversionRateRemoteDataSource(
        dataSource: ConversionRateApiDataSource
    ): ConversionRateRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAssetRepository(
        repository: AssetRepositoryImpl
    ): AssetRepository

    @Binds
    @Singleton
    abstract fun bindConversionRateRepository(
        repository: ConversionRateRepositoryImpl
    ): ConversionRateRepository

    @Binds
    @Singleton
    abstract fun bindSettingsStore(
        settingsRepository: PreferencesDataStoreRepository
    ): SettingsRepository
}
