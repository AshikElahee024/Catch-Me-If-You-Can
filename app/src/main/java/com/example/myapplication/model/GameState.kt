package com.example.myapplication.model

data class GameState(
    val players: List<Player>,
    val currentPlayerIndex: Int = 0,
    val roleRevealed: Boolean = false
)