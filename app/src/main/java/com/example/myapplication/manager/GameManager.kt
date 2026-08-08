package com.example.myapplication.manager

import com.example.myapplication.model.Player
import com.example.myapplication.model.Role


class GameManager {
    fun assignRoles(players: MutableList<Player>){
        val roles = mutableListOf(Role.KING, Role.QUEEN, Role.THIEF, Role.POLICE)
        roles.shuffle()

        for(i in players.indices){
            players[i].role = roles[i]
        }
    }

}