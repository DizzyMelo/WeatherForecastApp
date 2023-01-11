package com.study.weatherforecastapp.screens.favorite

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.study.weatherforecastapp.components.WeatherAppBar
import com.study.weatherforecastapp.model.Favorite
import com.study.weatherforecastapp.navigation.AppScreens
import com.study.weatherforecastapp.util.AppColors
import kotlinx.coroutines.flow.toList

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "Favorite Cities",
            navController = navController,
            isMainScreen = false,
            icon = Icons.Default.ArrowBack,
            onButtonClicked = {navController.popBackStack()}
        )
    }) {
        FavoriteList(viewModel = viewModel, navController = navController)
    }
}

@Composable
fun FavoriteList(viewModel: FavoriteViewModel, navController: NavController) {
    val favorites = viewModel.favorites.collectAsState().value
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyColumn(content = {
            items(items = favorites) { favorite ->
                CityRow(favorite = favorite, viewModel, navController)
            }
        })
    }
}

@Composable
fun CityRow(favorite: Favorite, viewModel: FavoriteViewModel, navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable {
                       navController.navigate("${AppScreens.MainScreen.name}/${favorite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(5.dp)),
        color = AppColors.Teal
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = favorite.city)
            Surface(
                modifier = Modifier,
                shape = CircleShape,
                color = Color.LightGray
            ) {
                Text(text = favorite.country, modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.caption)
            }
            IconButton(onClick = { viewModel.deleteFavorite(favorite) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete favorite icon",
                    tint = Color.Red.copy(alpha = .3f)
                )
            }
        }
    }
}