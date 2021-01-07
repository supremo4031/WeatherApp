package com.arpan.weatherapp.network

import com.arpan.weatherapp.model.weatherModels.City
import javax.inject.Inject

class ApiServiceImp @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getCityData(city: String) : City =
        apiService.getCityData(city)
}