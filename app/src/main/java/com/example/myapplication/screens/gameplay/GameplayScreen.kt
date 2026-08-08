package com.example.myapplication.screens.gameplay

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview

import com.example.myapplication.model.Player
import com.example.myapplication.model.Role

@Composable
fun GameplayScreen(player : Player){

    var roleRevealed by remember{
        mutableStateOf(false)
    }

    Column() {
        Text("The Hidden Thief")
        Text(player.name)

        if(roleRevealed){
            Text("YOUR ROLE")
            Text(player.role?.name ?: "Unknown") // Avoids throwing NullPointerException(Null Safety Call)
        }
        else{
            Text("Role Hidden")
        }

        if(roleRevealed){
            Button(onClick = {roleRevealed = false}) {
                Text("Hide Role")
            }
        }
        else{
            Button(onClick = {roleRevealed = true}) {
                Text("Reveal Role")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameplayScreenPreview(){
    GameplayScreen(Player("Ashik"))
}

