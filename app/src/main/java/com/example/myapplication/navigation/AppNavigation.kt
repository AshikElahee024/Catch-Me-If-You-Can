package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.myapplication.manager.GameManager
import com.example.myapplication.model.GameSession
import com.example.myapplication.model.Player
import com.example.myapplication.others.RulesScreen
import com.example.myapplication.others.SettingsScreen
import com.example.myapplication.screens.gameplay.GameplayScreen
import com.example.myapplication.screens.guessing.GuessingScreen
import com.example.myapplication.screens.home.HomeScreen
import com.example.myapplication.screens.player_setup.PlayerSetupScreen
import com.example.myapplication.screens.score.ScoreScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    var gameSession by remember {
        mutableStateOf(GameSession(players = emptyList()))
    }

    var isCorrectGuess by remember {
        mutableStateOf(false)
    }

    var roundScores by remember {
        mutableStateOf<Map<String, Int>>(emptyMap())
    }

    val gameManager = remember {
        GameManager()
    }

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(
                onPlayClick = {
                    navController.navigate("player_setup")
                },

                onSettingsClick = {
                    navController.navigate("settings")
                }
            )
        }


        composable("player_setup") {
            PlayerSetupScreen(
                onStartGame = { newPlayers ->

                    gameSession = GameSession(
                        players = newPlayers,
                        currentRound = 1,
                        totalScores = newPlayers.associate {
                            it.name to 0
                        }
                    )

                    navController.navigate("gameplay")
                },

                onRulesClick = {
                    navController.navigate("rules")
                }
            )
        }


        composable("settings") {
            SettingsScreen(
                onBack = {
                    navController.popBackStack()
                },

                onRulesClick = {
                    navController.navigate("rules")
                }
            )
        }

        composable("gameplay") {
                GameplayScreen(
                    players = gameSession.players.toMutableList(),

                    onAllRoleRevealed = { gamePlayers ->

                        gameSession = gameSession.copy(
                            players = gamePlayers
                        )

                        navController.navigate("guessing")
                    }
                )
            }


        composable("guessing") {
                GuessingScreen(
                    players = gameSession.players,

                    onContinue = { correctGuess ->

                        isCorrectGuess = correctGuess

                        roundScores = gameManager.calculateScores(
                            gameSession.players,
                            correctGuess
                        )

                        val newTotalScores =
                            gameManager.addRoundScores(
                                gameSession.totalScores,
                                roundScores
                            )

                        gameSession = gameSession.copy(
                            totalScores = newTotalScores
                        )

                        navController.navigate("score")
                    }
                )
        }


        composable("score") {
                ScoreScreen(
                    players = gameSession.players,
                    roundScores = roundScores,
                    totalScores = gameSession.totalScores,
                    isCorrectGuess = isCorrectGuess,

                    onPlayAgain = {

                        gameManager.startNewRound(
                            gameSession.players
                        )

                        gameSession = gameSession.copy(
                            currentRound = gameSession.currentRound + 1
                        )

                        navController.navigate("gameplay") {
                            popUpTo("player_setup")
                        }
                    }
                )
        }


        composable("rules") {
            RulesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}