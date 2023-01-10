package com.study.weatherforecastapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.study.weatherforecastapp.components.WeatherAppBar
import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.util.*
import com.study.weatherforecastapp.widgets.ForecastList
import com.study.weatherforecastapp.widgets.HumidityWindPressureRow
import com.study.weatherforecastapp.widgets.SunTimeRow
import com.study.weatherforecastapp.widgets.WeatherStateImage

@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true), producer = {
            value = viewModel.getWeatherData(city = "natal", units = "metric")
        }
    ).value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            navController = navController,
        ){

        }
    }) {
        MainContent(weather = weather)
    }
}

@Composable
fun MainContent(weather: Weather) {
    val weatherItem = weather.list.first()

    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
            text = formatDate(weather.list.first().dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = AppColors.Yellow,
            elevation = 5.dp
        ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                    WeatherStateImage(icon = weatherItem.weather.first().icon)
                    Text(
                        text = "${formatDecimal(weatherItem.temp.day)}º",
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = weatherItem.weather.first().main,
                        fontStyle = FontStyle.Italic
                    )
                }
            
        }

        HumidityWindPressureRow(weather = weatherItem)
        Divider()
        SunTimeRow(weather = weatherItem)
        Text(text = "This Week", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
        ForecastList(weather.list)
    }
}