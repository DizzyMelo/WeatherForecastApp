package com.study.weatherforecastapp.screens.main

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    ShowData(viewModel)
}

@Composable
fun ShowData(viewModel: MainViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true), producer = {
            value = viewModel.getWeatherData("Seattle")
        }
    ).value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null){
        Text(text = "Main Screen ${weatherData.data!!.city.country}")
    }

}