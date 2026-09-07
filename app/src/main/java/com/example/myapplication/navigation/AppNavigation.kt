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
        mutableStateOf<GameSession?>(null)
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

        // HOME
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


        // PLAYER SETUP
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


        // SETTINGS
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


        // GAMEPLAY
        composable("gameplay") {

            gameSession?.let { session ->

                GameplayScreen(
                    players = session.players.toMutableList(),

                    onAllRoleRevealed = { gamePlayers ->

                        gameSession = session.copy(
                            players = gamePlayers
                        )

                        navController.navigate("guessing")
                    }
                )
            }
        }


        // GUESSING
        composable("guessing") {

            gameSession?.let { session ->

                GuessingScreen(
                    players = session.players,

                    onContinue = { correctGuess ->

                        isCorrectGuess = correctGuess

                        // Calculate this round's scores
                        roundScores = gameManager.calculateScores(
                            session.players,
                            correctGuess
                        )

                        // Add this round's scores
                        val newTotalScores =
                            gameManager.addRoundScores(
                                session.totalScores,
                                roundScores
                            )

                        // Update GameSession
                        gameSession = session.copy(
                            totalScores = newTotalScores
                        )

                        navController.navigate("score")
                    }
                )
            }
        }


        // SCORE
        composable("score") {

            gameSession?.let { session ->

                ScoreScreen(
                    players = session.players,
                    roundScores = roundScores,
                    totalScores = session.totalScores,
                    isCorrectGuess = isCorrectGuess,

                    onPlayAgain = {

                        gameManager.startNewRound(
                            session.players
                        )

                        gameSession = session.copy(
                            currentRound = session.currentRound + 1
                        )

                        navController.navigate("gameplay") {
                            popUpTo("player_setup")
                        }
                    }
                )
            }
        }


        // RULES
        composable("rules") {

            RulesScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}