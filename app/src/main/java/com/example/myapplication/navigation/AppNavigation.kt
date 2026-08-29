package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.home.BlankScreen
import com.example.myapplication.screens.home.HomeScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.model.Player
import com.example.myapplication.screens.gameplay.GameplayScreen
import com.example.myapplication.screens.player_setup.PlayerSetupScreen
import com.example.myapplication.others.RulesScreen
import com.example.myapplication.screens.guessing.GuessingScreen

@Composable
fun AppNavigation(){
    val navController = rememberNavController()

    var players by remember {
        mutableStateOf<List<Player>>(emptyList())
    }

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
                onStartGame = { newPlayers ->
                    players = newPlayers
                    navController.navigate("gameplay")
                },

                onRulesClick = {
                    navController.navigate("rules")
                }
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

        composable("gameplay") {
            GameplayScreen(
                players = players.toMutableList(),
                onAllRoleRevealed = { gamePlayers ->
                    players = gamePlayers
                    navController.navigate("guessing")
                }
            )
        }

        composable("guessing"){
            GuessingScreen(
                players = players,
                onGuess = {
                    navController.navigate("score")
                }
            )
        }

        composable("score"){
            BlankScreen(
                title = "Score Screen",
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("rules"){
            RulesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}