package com.study.weatherforecastapp.screens.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.study.weatherforecastapp.R
import com.study.weatherforecastapp.components.WeatherAppBar
import com.study.weatherforecastapp.data.DataOrException
import com.study.weatherforecastapp.model.Weather
import com.study.weatherforecastapp.model.WeatherItem
import com.study.weatherforecastapp.util.*

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

@Composable
fun ForecastList(items: List<WeatherItem>) {
    Surface(
        color = AppColors.LightGray,
        shape = RoundedCornerShape(15.dp)
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 1.dp),
            content = {
            items(items) { item ->
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp),
                    color = Color.White,
                    shape = CircleShape.copy(topEnd = CornerSize(2.dp))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 3.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = formatDate(item.dt).subSequence(0, 3).toString())
                        WeatherStateImage(icon = item.weather.first().icon)
                        Surface(
                            color = AppColors.Yellow,
                            shape = CircleShape
                        ) {
                            Text(
                                text = item.weather.first().description,
                                modifier = Modifier
                                    .padding(vertical = 2.dp, horizontal = 4.dp),
                                style = MaterialTheme.typography.caption
                            )
                        }
                        Row {
                            Text(text = "${formatDecimal(item.temp.max)}º", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.Blue.copy(alpha = 0.7f))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "${formatDecimal(item.temp.min)}º", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = Color.LightGray)
                        }
                    }
                }
            }
        })
    }
}

@Composable
fun WeatherStateImage(icon: String) {
    val imageUrl = "${Constants.IMG_BASE_URL}${icon}.png"
    AsyncImage(
        model = imageUrl,
        contentDescription = "Weather representation icon",
        modifier = Modifier.size(80.dp)
    )
}

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HWPItem(icon = R.drawable.humidity, amount = weather.humidity.toString(), unit = "%")
        HWPItem(icon = R.drawable.pressure, amount = weather.pressure.toString(), unit = "psi")
        HWPItem(icon = R.drawable.wind, amount = weather.speed.toString(), unit = " mph")
    }
}

@Composable
fun HWPItem(icon: Int, amount: String, unit: String) {
    Row(modifier = Modifier.padding(4.dp)) {
        Icon(painter = painterResource(id = icon), contentDescription = "HWP icon", modifier = Modifier.size(12.dp))
        Text(text = "$amount$unit", style = MaterialTheme.typography.caption)
    }
}

@Composable
fun SunTimeRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SunTimeItem(icon = R.drawable.sunrise, time = formatDateTime(weather.sunrise))
        SunTimeItem(icon = R.drawable.sunset, time = formatDateTime(weather.sunset), iconFirst = false)
    }
}

@Composable
fun SunTimeItem(icon: Int, time: String, iconFirst: Boolean = true) {
    if (iconFirst) {
        return Row(
            modifier = Modifier.padding(4.dp),
        ) {
            Icon(painter = painterResource(id = icon), contentDescription = "Sun time icon", modifier = Modifier.size(25.dp))
            Text(text = time, style = MaterialTheme.typography.caption)
        }
    }

    Row(
        modifier = Modifier.padding(4.dp),
    ) {
        Text(text = time, style = MaterialTheme.typography.caption)
        Icon(painter = painterResource(id = icon), contentDescription = "Sun time icon", modifier = Modifier
            .size(25.dp)
            .padding(4.dp))
    }

}
