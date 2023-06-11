package ru.apmgor.di

import android.content.Context
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.apmgor.data.database.ForecastDatabase
import ru.apmgor.data.datasource.CityLocalDataSource
import ru.apmgor.data.datasource.CityRemoteDataSource
import ru.apmgor.data.datasource.ForecastLocalDataSource
import ru.apmgor.data.datasource.ForecastRemoteDataSource
import ru.apmgor.data.datasource.UnitsDataSource
import ru.apmgor.data.gps.CoordinatesProvider
import ru.apmgor.data.gps.CoordinatesProviderImpl
import ru.apmgor.data.network.CityApiService
import ru.apmgor.data.network.CityRemoteDataSourceImpl
import ru.apmgor.data.network.ForecastApiService
import ru.apmgor.data.network.ForecastRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindCityRemoteDataSource(impl: CityRemoteDataSourceImpl): CityRemoteDataSource

    @Singleton
    @Binds
    fun bindCoordinatesDataSource(impl: CoordinatesProviderImpl): CoordinatesProvider

    @Singleton
    @Binds
    fun bindForecastRemoteDataSource(impl: ForecastRemoteDataSourceImpl): ForecastRemoteDataSource

    companion object {

        private const val API_BASE_URL = "http://api.openweathermap.org/"
        private const val REQUEST_INTERVAL = 10_000L

        @Singleton
        @Provides
        fun provideOkHttpClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .followSslRedirects(true)
                .build()
        }

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun provideCityApiService(retrofit: Retrofit): CityApiService =
            retrofit.create()

        @Provides
        fun provideFusedLocationProviderClient(@ApplicationContext appContext: Context) =
            LocationServices.getFusedLocationProviderClient(appContext)

        @Provides
        fun provideLocationRequest() = LocationRequest.Builder(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            REQUEST_INTERVAL
        ).build()

        @Provides
        @Singleton
        fun provideDatabase(
            @ApplicationContext appContext: Context
        ) : ForecastDatabase =
            ForecastDatabase.getInstance(appContext.applicationContext)

        @Provides
        @Singleton
        fun provideCityLocalDataSource(db: ForecastDatabase): CityLocalDataSource =
            db.getCityLocalDataSource()

        @Provides
        @Singleton
        fun provideForecastLocalDataSource(db: ForecastDatabase): ForecastLocalDataSource =
            db.getForecastLocalDataSource()

        @Provides
        @Singleton
        fun provideUnitsDataSource(db: ForecastDatabase): UnitsDataSource =
            db.getUnitsDataSource()

        @Singleton
        @Provides
        fun provideForecastApiService(retrofit: Retrofit): ForecastApiService =
            retrofit.create()

        @Provides
        fun provideMainScope(): CoroutineScope =
            CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    }
}