package com.coolkosta.dailyplannerapp.navigation

import androidx.annotation.StringRes
import com.coolkosta.dailyplannerapp.R

enum class Screen(@StringRes val title: Int) {
    SplashScreen(title = R.string.home_screen),
    HomeScreen(title = R.string.splash_screen)
}