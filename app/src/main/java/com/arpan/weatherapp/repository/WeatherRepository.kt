package com.arpan.weatherapp.repository

import com.arpan.weatherapp.model.weatherModels.City
import com.arpan.weatherapp.network.ApiServiceImp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val apiServiceImp: ApiServiceImp
) {

    fun getCityData(city: String) : Flow<City> = flow {
        val response = apiServiceImp.getCityData(city)
        emit(response)
    }.flowOn(Dispatchers.IO)
        .conflate()
}