package com.coolkosta.dailyplannerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.coolkosta.dailyplannerapp.screen.home.HomeScreen
import com.coolkosta.dailyplannerapp.screen.splash.SplashScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.name,
        modifier = modifier
    ) {

        composable(
            route = Screen.SplashScreen.name
        ) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
    }
}