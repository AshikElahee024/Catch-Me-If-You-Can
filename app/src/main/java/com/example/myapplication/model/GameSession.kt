package com.example.myapplication.model

data class GameSession(
    val players : List<Player>,
    var currentRound : Int = 1,
    val totalScores : Map<String, Int> = emptyMap(),
    val isCorrectGuess: Boolean? = null
)