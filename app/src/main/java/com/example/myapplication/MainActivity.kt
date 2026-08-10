package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.home.HomeScreen
import com.example.myapplication.screens.home.BlankScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplication()
        }
    }
}

@Composable
fun MyApplication() {
    MaterialTheme {
        Surface {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "home") {

                composable("home") {
                    HomeScreen(
                        onPlayClick = { navController.navigate("play_blank") },
                        onSettingsClick = { navController.navigate("settings_blank") }
                    )
                }

                composable("play_blank") {
                    BlankScreen(
                        title = "Game Screen",
                        onBack = { navController.popBackStack() }
                    )
                }

                composable("settings_blank") {
                    BlankScreen(
                        title = "Settings Screen",
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}