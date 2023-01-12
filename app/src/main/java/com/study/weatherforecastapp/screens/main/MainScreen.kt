package com.study.weatherforecastapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.study.weatherforecastapp.components.WeatherAppBar
import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.navigation.AppScreens
import com.study.weatherforecastapp.screens.settings.SettingsViewModel
import com.study.weatherforecastapp.util.*
import com.study.weatherforecastapp.widgets.ForecastList
import com.study.weatherforecastapp.widgets.HumidityWindPressureRow
import com.study.weatherforecastapp.widgets.SunTimeRow
import com.study.weatherforecastapp.widgets.WeatherStateImage
import com.study.weatherforecastapp.model.Unit

@Composable
fun MainScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String? = "natal"
) {
    val unitFromDb = settingsViewModel.units.collectAsState().value
    var unit by remember {
        mutableStateOf(Constants.IMPERIAL_UNIT)
    }
    var symbol by remember {
        mutableStateOf("F")
    }

    if (unitFromDb.isNotEmpty()) {
        unit = unitFromDb.first().unit
        symbol = unitFromDb.first().symbol
    }

    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true), producer = {
            value = mainViewModel.getWeatherData(city = city!!, units = unit)
        }
    ).value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        val unitObject = Unit(unit, symbol)
        MainScaffold(weather = weatherData.data!!, navController = navController, unit = unitObject)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScaffold(weather: Weather, navController: NavController, unit: Unit) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(route = AppScreens.SearchScreen.name)
            }
        ){

        }
    }) {
        MainContent(weather = weather, unit = unit)
    }
}

@Composable
fun MainContent(weather: Weather, unit: Unit) {
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
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )) {
                                append("${formatDecimal(weatherItem.temp.day)}º")
                            }
                            withStyle(style = SpanStyle(
                                fontSize = 17.sp
                            )) {
                                append(unit.symbol)
                            }
                        }
                    )

                    //"${formatDecimal(weatherItem.temp.day)}º ${unit.symbol}"
                    Text(
                        text = weatherItem.weather.first().main,
                        fontStyle = FontStyle.Italic
                    )
                }
            
        }

        HumidityWindPressureRow(weather = weatherItem, unit = unit.unit)
        Divider()
        SunTimeRow(weather = weatherItem)
        Text(text = "This Week", style = MaterialTheme.typography.subtitle1, fontWeight = FontWeight.Bold)
        ForecastList(weather.list)
    }
}