package com.example.myapplication.screens.gameplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Greeting
import com.example.myapplication.manager.GameManager
import com.example.myapplication.model.GameState
import com.example.myapplication.model.Player

private val BackgroundColor = Color(0xFF6E151F)
private val CardColor = Color(0xFF160A21)
private val YellowColor = Color(0xFFFFE000)
private val PurpleBorder = Color(0xFF4B3457)
private val WhiteText = Color(0xFFF5F0F7)
private val GrayText = Color(0xFFB8AEBE)
@Composable
fun GameplayScreen() {
    val players = remember {
        mutableListOf<Player>(
            Player("Ashik"),
            Player("Satu"),
            Player("Sami"),
            Player("Dibbo")
        )
    }

    val gameManager = remember { GameManager() }

    remember {
        gameManager.assignRoles(players)
        true
    }

    var gameState by remember {
        mutableStateOf(GameState(players = players))
    }

    val currentPlayer = gameManager.getCurrentPlayer(gameState)

    Box(
        modifier = Modifier.fillMaxSize().background(BackgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 30.dp,
                    bottom = 20.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "THE HIDDEN THIEF",
                color = YellowColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(GrayText),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Player ${gameState.currentPlayerIndex + 1} : ${currentPlayer.name}",
                    color = CardColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }


            Spacer(modifier = Modifier.height(40.dp))

            roleRevealCard(
                roleRevealed = gameState.roleRevealed,
                roleName = currentPlayer.role?.name ?: "Unknown"
            )

            Spacer(modifier = Modifier.weight(1f))

            if(!gameState.roleRevealed){
                Button(
                    onClick = {
                        gameState = gameState.copy(roleRevealed = true)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = GrayText,
                        containerColor = YellowColor
                    )
                ) {
                    Text(
                        text = "TAP TO REVEAL ROLE",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Make sure nobody else is looking at the screen",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(50.dp))
            }

            else{
                Button(
                    onClick = {
                        if (!gameManager.isLastPlayer(gameState)){
                            gameState = gameManager.moveToNextPlayer(gameState)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = GrayText,
                        containerColor = YellowColor
                    )
                ) {
                    Text(
                        text = "NEXT PLAYER",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "KEEP YOUR ROLE SECRET!!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@Composable
private fun roleRevealCard(roleRevealed : Boolean, roleName : String){
    val cardModifier = Modifier.fillMaxWidth().height(300.dp).clip(RoundedCornerShape(22.dp)).background(CardColor).padding(20.dp)
    if(!roleRevealed) {
        Column(
            modifier = cardModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "SECRET",
                color = WhiteText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = "ROLE",
                color = WhiteText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
    else{
        Column(
            modifier = cardModifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "YOUR ROLE",
                color = YellowColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = roleName.uppercase(),
                color = YellowColor,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
        }
    }
}



@Preview(
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
fun GameplayScreenPreview(){
    GameplayScreen()
}
