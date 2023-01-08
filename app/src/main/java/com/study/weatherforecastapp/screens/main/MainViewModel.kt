package com.study.weatherforecastapp.screens.main

import androidx.lifecycle.ViewModel
import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {
    suspend fun getWeatherData(city: String, units: String) : DataOrException<Weather, Boolean, Exception> =
        repository.getWeather(city, units)
}