package com.arpan.weatherapp.network

import com.arpan.weatherapp.model.weatherModels.City
import retrofit2.http.GET
import retrofit2.http.Query
import com.arpan.weatherapp.utils.Constants.Companion.API_KEY

interface ApiService {

    @GET("weather")
    suspend fun getCityData(
        @Query("q") q : String,
        @Query("appid") appId : String = API_KEY
    ) : City
}