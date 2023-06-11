package ru.apmgor.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.apmgor.domain.repo.CityRepository
import ru.apmgor.data.repo.CityRepositoryImpl
import ru.apmgor.domain.repo.ForecastRepository
import ru.apmgor.data.repo.ForecastRepositoryImpl
import ru.apmgor.domain.repo.UnitsRepository
import ru.apmgor.data.repo.UnitsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Singleton
    @Binds
    fun bindCityRepository(impl: CityRepositoryImpl): CityRepository

    @Singleton
    @Binds
    fun bindForecastRepository(impl: ForecastRepositoryImpl): ForecastRepository

    @Singleton
    @Binds
    fun bindUnitsDataSource(impl: UnitsRepositoryImpl): UnitsRepository
}