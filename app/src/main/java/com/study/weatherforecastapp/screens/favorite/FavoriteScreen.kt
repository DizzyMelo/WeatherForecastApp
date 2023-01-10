package com.study.weatherforecastapp.screens.favorite

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun FavoriteScreen(navController: NavController, viewModel: FavoriteViewModel) {
    Text(text = "Favorite")
}