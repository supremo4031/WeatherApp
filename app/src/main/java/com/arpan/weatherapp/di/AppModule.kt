package com.arpan.weatherapp.di

import com.arpan.weatherapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    fun providesBaseUrl() : String = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun providesRetrofitInstance(
        baseUrl : String
    ) : Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    fun providesApiService(retrofit: Retrofit) : ApiService =
        retrofit.create(ApiService::class.java)
}