package com.study.weatherforecastapp.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.study.weatherforecastapp.components.WeatherAppBar
import com.study.weatherforecastapp.model.Unit
import com.study.weatherforecastapp.util.AppColors

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, viewModel: SettingsViewModel = hiltViewModel()) {
    var unitToggleState by remember {
        mutableStateOf(false)
    }
    val imperial = "Imperial (F)"
    val metric = "Metric (C)"
    val choiceFromDb = viewModel.units.collectAsState().value

    val defaultChoice = if (choiceFromDb.isEmpty()) imperial else choiceFromDb.first().unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Settings",
            navController = navController,
            isMainScreen = false,
            elevation = 0.dp,
            icon = Icons.Default.ArrowBack,
            onButtonClicked = { navController.popBackStack() },

            )
    }) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp)
                )
                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) {
                            choiceState = imperial
                            return@IconToggleButton
                        }
                        choiceState = metric
                    },
                    modifier = Modifier
                        .fillMaxWidth(.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = .6f))
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit F" else "Celsius C")
                }

                Button(
                    onClick = {
                        viewModel.deleteAllUnits()
                        viewModel.addUnit(Unit(unit = choiceState.ifEmpty { imperial }))
                    },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColors.Yellow
                    )
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }
            }
        }
    }
}