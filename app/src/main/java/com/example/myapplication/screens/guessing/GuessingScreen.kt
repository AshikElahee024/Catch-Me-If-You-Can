package com.example.myapplication.screens.guessing

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.myapplication.model.Player

private val BackgroundColor = Color(0xFF6E151F)
private val CardColor = Color(0xFF160A21)
private val YellowColor = Color(0xFFFFE000)
private val WhiteText = Color(0xFFF5F0F7)
private val GrayText = Color(0xFFB8AEBE)

var players = listOf<Player>()
@Composable
fun GuessingScreen(
    players : List<Player>,
    onGuess : () -> Unit
){
    val knight = players.find {it.role?.name == "KNIGHT"}

    val guess = players.filter { it.role?.name != "KNIGHT" }

    var selectedPlayer by remember {
        mutableStateOf<Player?>(null)
    }

    val guessingFinished = selectedPlayer != null

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 30.dp,
                    bottom = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "WHO IS THE THIEF?",
                color = WhiteText,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(35.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                GuessCard(
                    player = guess[0],
                    isSelected = selectedPlayer == guess[0],
                    guessFinished = guessingFinished,
                    onClick = {
                        if(!guessingFinished){
                            selectedPlayer = guess[0]
                        }
                    }
                )

                GuessCard(
                    player = guess[1],
                    isSelected = selectedPlayer == guess[1],
                    guessFinished = guessingFinished,
                    onClick = {
                        if(!guessingFinished){
                            selectedPlayer = guess[1]
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            GuessCard(
                player = guess[2],
                isSelected = selectedPlayer == guess[2],
                guessFinished = guessingFinished,
                onClick = {
                    if(!guessingFinished){
                        selectedPlayer = guess[2]
                    }
                }
            )
        }

    }
}

@Composable
fun GuessCard(
    player : Player,
    isSelected : Boolean,
    guessFinished : Boolean,
    onClick : () -> Unit
){
    Box(
        modifier = Modifier
            .width(150.dp)
            .height(210.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(CardColor)
            .clickable(
                enabled = !guessFinished
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isSelected) {
                Text(
                    text = player.name.uppercase(),
                    color = WhiteText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "ROLE",
                    color = YellowColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = player.role?.name ?: "UNKNOWN",
                    color = YellowColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            } else {

                Text(
                    text = player.name.uppercase(),
                    color = WhiteText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "TAP TO GUESS",
                    color = YellowColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun GuessingScreenPreview(){
    GuessingScreen(
        players = players,
        onGuess = {}
    )
}