package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.home.BlankScreen
import com.example.myapplication.screens.home.HomeScreen
import com.example.myapplication.screens.player_setup.PlayerSetupScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ){
        composable("home"){
            HomeScreen(
                onPlayClick = {
                    navController.navigate("player_setup")
                },

                onSettingsClick = {
                    navController.navigate("settings")
                }
            )
        }

        composable("player_setup"){
            PlayerSetupScreen(
                onStartGame = {players -> }
            )
        }

        composable("settings"){
            BlankScreen(
                title = "Settings Screen",
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}