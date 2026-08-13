package com.example.myapplication.screens.gameplay

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.manager.GameManager
import com.example.myapplication.model.GameState
import com.example.myapplication.model.Player
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween

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
            Player("Wrivu")
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

    var cardRotation = remember {
        Animatable(0f)
    }

    var isAnimating by remember { // Prevents Multiple Click
        mutableStateOf(false)
    }

    var scope = rememberCoroutineScope()

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
                    .background(Color(0x00000000)),
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
                roleName = currentPlayer.role?.name ?: "Unknown",
                rotation = cardRotation.value
            )

            Spacer(modifier = Modifier.weight(1f))

            if(!gameState.roleRevealed){
                Button(
                    onClick = {
                        if(!isAnimating){
                            scope.launch {
                                isAnimating = true

                                gameState = gameState.copy(roleRevealed = true)

                                cardRotation.animateTo(
                                    targetValue = 180f,
                                    animationSpec = tween(700)
                                )

                                isAnimating = false
                            }
                        }
                    },
                    enabled = !isAnimating,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = GrayText,
                        containerColor = YellowColor
                    )
                ) {
                    Text(
                        text = "REVEAL ROLE",
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
                        if (!isAnimating && !gameManager.isLastPlayer(gameState)){
                            scope.launch {
                                isAnimating = true

                                cardRotation.animateTo(
                                    targetValue = 0f,
                                    animationSpec = tween(700)
                                )

                                gameState = gameManager.moveToNextPlayer(gameState)

                                isAnimating = false
                            }
                        }
                    },
                    enabled = !isAnimating,
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
private fun roleRevealCard(
    roleRevealed: Boolean,
    roleName: String,
    rotation: Float
) {

    val cardImage = getRoleCard(roleName)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            },
        contentAlignment = Alignment.Center
    ) {

        if (rotation <= 90f) {

            androidx.compose.foundation.Image(
                painter = painterResource(
                    id = R.drawable.card_back
                ),
                contentDescription = "Hidden role card",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(22.dp))
            )
        }
        else {
            androidx.compose.foundation.Image(
                painter = painterResource(
                    id = cardImage
                ),
                contentDescription = "Role card",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(22.dp))
                    .graphicsLayer {

                        rotationY = 180f
                    }
            )
        }
    }
}


private fun getRoleCard(roleName : String) : Int{ // Int -> Reference ID to the Image
    return when(roleName.uppercase()){ // Switch
        "KING" -> R.drawable.king_card
        "PRINCE" -> R.drawable.prince_card
        "KNIGHT" -> R.drawable.knight_card
        "THIEF" -> R.drawable.thief_card
        else -> R.drawable.card_back
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
