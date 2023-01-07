package com.study.weatherforecastapp.network

import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid") key: String = Constants.API_KEY,
    ) : Weather
}