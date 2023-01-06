package com.study.weatherforecastapp.model

data class Weather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherObject>,
    val message: Double
)