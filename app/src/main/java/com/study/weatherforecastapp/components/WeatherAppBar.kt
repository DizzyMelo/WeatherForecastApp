package com.study.weatherforecastapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun WeatherAppBar(
    title: String = "City, CT",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 5.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = title) },
        elevation = elevation,
        modifier = Modifier.padding(3.dp),
        backgroundColor = Color.Transparent,
        navigationIcon = {
             if(icon != null) {
                 IconButton(onClick = { onButtonClicked.invoke() }) {
                     Icon(
                         imageVector = icon,
                         contentDescription = "Arrow back icon",
                         tint = MaterialTheme.colors.onSecondary
                     )
                 }
             }
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "App Bar Search Icon")
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "App Bar Menu Icon")
                }
            }
        }
    )
}