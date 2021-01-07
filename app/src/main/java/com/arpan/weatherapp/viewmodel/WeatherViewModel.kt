package com.arpan.weatherapp.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arpan.weatherapp.model.weatherModels.City
import com.arpan.weatherapp.repository.WeatherRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class WeatherViewModel @ViewModelInject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val getData : MutableLiveData<City> = MutableLiveData()
    val searchCity = ConflatedBroadcastChannel<String>()

    fun getSearchData(city: String) {
        searchCity.offer(city)
    }

    @FlowPreview
    fun getCityData() = viewModelScope.launch {
        searchCity.asFlow()
            .flatMapLatest { city ->
                weatherRepository.getCityData(city)
            }.catch { e ->
                Log.d("main", "getCityData: " + e.message)
            }.collect { city ->
                getData.postValue(city)
            }
    }
}