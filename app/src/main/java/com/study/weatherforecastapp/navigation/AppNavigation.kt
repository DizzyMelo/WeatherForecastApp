package com.study.weatherforecastapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.study.weatherforecastapp.screens.about.AboutScreen
import com.study.weatherforecastapp.screens.favorite.FavoriteScreen
import com.study.weatherforecastapp.screens.favorite.FavoriteViewModel
import com.study.weatherforecastapp.screens.main.MainScreen
import com.study.weatherforecastapp.screens.main.MainViewModel
import com.study.weatherforecastapp.screens.search.SearchScreen
import com.study.weatherforecastapp.screens.settings.SettingsScreen
import com.study.weatherforecastapp.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name ) {
        composable(route = AppScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        val mainScreenRoute = AppScreens.MainScreen.name
        composable(
            route = "$mainScreenRoute/{city}",
            arguments = listOf(
                navArgument(name = "city", builder = {
                    type = NavType.StringType
                })
            )) { navBackStackEntry ->

            navBackStackEntry.arguments?.getString("city").let {
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(navController = navController, viewModel = mainViewModel, city = it)
            }
        }

        composable(route = AppScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(route = AppScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(route = AppScreens.FavoriteScreen.name) {
            val favoriteViewModel = hiltViewModel<FavoriteViewModel>()
            FavoriteScreen(navController = navController, viewModel = favoriteViewModel)
        }

        composable(route = AppScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}
