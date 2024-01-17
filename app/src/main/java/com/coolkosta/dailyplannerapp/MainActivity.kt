package com.coolkosta.dailyplannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.coolkosta.dailyplannerapp.ui.theme.DailyPlannerAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyPlannerAppTheme {
               DailyPlannerApp()
            }
        }
    }
}

