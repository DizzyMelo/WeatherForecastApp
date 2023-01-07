package com.study.weatherforecastapp.repository

import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(city: String, units: String = "imperial"): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = city, units = units)
        } catch (ex: Exception) {
            return DataOrException(null, false, ex)
        }
        return DataOrException(data = response)
    }

}