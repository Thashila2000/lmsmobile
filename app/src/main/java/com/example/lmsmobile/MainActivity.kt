package com.example.lmsmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.lmsmobile.navigation.AppNavHost
import com.example.lmsmobile.ui.theme.LmsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LmsTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}