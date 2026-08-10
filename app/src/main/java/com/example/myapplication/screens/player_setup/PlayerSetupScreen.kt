package com.example.myapplication.screens.player_setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemGesturesPadding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.screens.gameplay.GameplayScreen
import com.example.myapplication.model.Player

/*
A callback function is a function passed into another function as an argument.
The outer function runs this callback function later,
usually after a task finishes, an event happens, or a timer ends.
*/

@Composable
fun PlayerSetupScreen(
    onStartGame : (List<Player>) -> Unit // Callback function
){
    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }
    var player3 by remember { mutableStateOf("") }
    var player4 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "THE HIDDEN THIEF",
            fontSize = 28.sp
        )

        Text(
            text = "Enter Player Names",
            fontSize = 22.sp,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = player1, // Display whatever is currently stored in player1.
            onValueChange = {player1 = it}, // Whenever the user changes the text(Types his name), store the new text in player1.
            label = {Text("Player 1")},
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = player2,
            onValueChange = {player2 = it},
            label = {Text("Player 2")},
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = player3,
            onValueChange = {player3 = it},
            label = {Text("Player 3")},
            modifier = Modifier.padding(bottom = 12.dp)
        )

        OutlinedTextField(
            value = player4,
            onValueChange = {player4 = it},
            label = {Text("Player 4")},
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Button(
            onClick = {
                if(player1.isNotBlank() && player2.isNotBlank() && player3.isNotBlank() && player4.isNotBlank()) {
                    val players = listOf(
                        Player(player1.trim()),
                        Player(player2.trim()),
                        Player(player3.trim()),
                        Player(player4.trim())
                    )
                    onStartGame(players)
                }
            }
        ) {
            Text("START GAME")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerSetupScreenScreenPreview(){
    PlayerSetupScreen(
        onStartGame = {players ->
            println("Players : ")
            players.forEach {
                println(it.name)
            }
        }
    )
}