package com.example.myapplication.manager

import com.example.myapplication.model.Player
import com.example.myapplication.model.Role
import com.example.myapplication.model.GameState


class GameManager {
    fun assignRoles(players: MutableList<Player>){
        val roles = mutableListOf(Role.KING, Role.PRINCE, Role.THIEF, Role.KNIGHT)
        roles.shuffle()

        for(i in players.indices){
            players[i].role = roles[i]
        }
    }

    fun getCurrentPlayer(gameState : GameState) : Player {
        return gameState.players[gameState.currentPlayerIndex]
    }

    fun moveToNextPlayer(gameState : GameState) : GameState{
        return gameState.copy( // Replica with selective properties changed
            currentPlayerIndex = gameState.currentPlayerIndex + 1,
            roleRevealed = false
        )
    }

    fun isLastPlayer(gameState: GameState) : Boolean {
        return gameState.currentPlayerIndex == gameState.players.lastIndex
    }

    fun startNewRound(players: List<Player>) {
        assignRoles(players.toMutableList())
    }

    fun calculateScores( players: List<Player>, isCorrectGuess: Boolean ): Map<String, Int> {
        return players.associate { player ->

            val score = when (player.role) {

                Role.KING -> 100

                Role.PRINCE -> 80

                Role.KNIGHT -> {
                    if (isCorrectGuess) 90 else 0
                }

                Role.THIEF -> {
                    if (isCorrectGuess) 0 else 90
                }

                else -> 0
            }

            player.name to score
        }
    }

    fun addRoundScores(totalScores: Map<String, Int>, roundScores: Map<String, Int>): Map<String, Int> {
        return roundScores.mapValues { (playerName, roundScore) ->
            (totalScores[playerName] ?: 0) + roundScore
        }
    }
}