package com.coolkosta.dailyplannerapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.coolkosta.dailyplannerapp.navigation.Navigation

@Composable
fun DailyPlannerApp(
    navController: NavHostController = rememberNavController(),
) {
    Navigation(navController = navController, modifier = Modifier.fillMaxSize())
}