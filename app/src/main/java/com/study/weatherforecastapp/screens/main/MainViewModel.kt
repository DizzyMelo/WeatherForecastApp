package com.study.weatherforecastapp.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    var city = "lisbon"
    val data: MutableState<DataOrException<Weather, Boolean, Exception>> =
        mutableStateOf(DataOrException(data = null, loading = true, exception = Exception("")))

    init {
        loadWeather()
    }

    private fun loadWeather() {
        getWeather(city = "Seattle")
    }

    private fun getWeather(city: String) {
        if (city.isEmpty()) return

        viewModelScope.launch {
            repository.getWeather(city = city)
            data.value.loading = true
            data.value = repository.getWeather(city)

            if (data.value.data.toString().isNotEmpty()) {
                data.value.loading = false
            }
        }

        Log.d("GET", "getWeather: ${data.value.data.toString()}")
    }
}