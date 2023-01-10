package com.study.weatherforecastapp.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.study.weatherforecastapp.R
import com.study.weatherforecastapp.model.WeatherItem
import com.study.weatherforecastapp.util.*

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
