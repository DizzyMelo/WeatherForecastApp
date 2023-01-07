package com.study.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.study.weatherforecastapp.screens.main.MainScreen
import com.study.weatherforecastapp.screens.main.MainViewModel
import com.study.weatherforecastapp.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name ) {
        composable(route = AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        composable(route = AppScreens.MainScreen.name) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            MainScreen(navController = navController, viewModel = mainViewModel)
        }
    }
}
