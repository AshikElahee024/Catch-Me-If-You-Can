package com.example.myapplication.screens.gameplay

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.manager.GameManager
import com.example.myapplication.model.GameState
import com.example.myapplication.model.Player
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun GameplayScreen(){

    val players = remember{ // to retain value after recompose / redraw (change of UI state)
        mutableListOf< Player>(
            Player("Ashik"),
            Player("Satu"),
            Player("Sami"),
            Player("Dibya")
        )
    }

    val gameManager = remember { GameManager() }

    remember {
        gameManager.assignRoles(players)
        true
    }

    var gameState by remember{ // var gameState = GameState(players)
        mutableStateOf(GameState(players = players))
    }

    val currentPlayer = gameManager.getCurrentPlayer(gameState)

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "The Hidden Thief",
            fontSize = 28.sp
        )
        Text(
            text = "Player ${gameState.currentPlayerIndex + 1}",
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 30.dp)
        )
        Text(
            text = currentPlayer.name,
            fontSize = 26.sp,
            modifier = Modifier.padding(top = 10.dp)

        )

        if(gameState.roleRevealed){
            Text(
                text = "YOUR ROLE",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 30.dp)
            )

            Text(
                text = currentPlayer.role?.name ?: "Unknown", // Avoids throwing NullPointerException(Null Safety Call)
                fontSize = 36.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            Button(onClick = {
                if(!gameManager.isLastPlayer(gameState)) {
                    gameState = gameManager.moveToNextPlayer(gameState)
                }
            },
                modifier = Modifier.padding(top = 30.dp)
            ) {
                Text("Next Player")
            }
        }
        else{
            Text(
                text = "Role Hidden",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 30.dp)
            )
            Button(onClick = {
                gameState = gameState.copy(roleRevealed = true)
            },
                modifier = Modifier.padding(top = 30.dp)
            ) {
                Text("Reveal Role")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameplayScreenPreview(){
    GameplayScreen()
}

