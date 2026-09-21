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
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.example.myapplication.R
import com.example.myapplication.model.Player
import com.example.myapplication.model.Role

private val BackgroundColor = Color(0xFF6E1418)
private val CardColor = Color(0xFF160A21)
private val YellowColor = Color(0xFFFFE000)
private val WhiteText = Color(0xFFF5F0F7)
private val GrayText = Color(0xFFB8AEBE)

@Composable
fun GuessingScreen(
    players : List<Player>,
    onContinue : (Boolean) -> Unit
){
    val knight = players.find {it.role?.name == "KNIGHT"}

    val guess = players.filter { it.role?.name != "KNIGHT" }

    var selectedPlayer by remember {
        mutableStateOf<Player?>(null)
    }

    val isCorrectGuess = selectedPlayer?.role == Role.THIEF

    var cardRotation = remember {
        Animatable(0f)
    }

    var isGuessRevealed by remember {
        mutableStateOf(false)
    }

    var isAnimating by remember {
        mutableStateOf(false)
    }

    var scope = rememberCoroutineScope()

    val guessingFinished = isGuessRevealed

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
                    rotation = if(selectedPlayer == guess[0]){
                        cardRotation.value
                    } else{
                        0f
                    },
                    guessingFinished = guessingFinished,
                    isAnimating = isAnimating,
                    onClick = {
                        if(selectedPlayer == null && !isAnimating){
                            scope.launch {
                                isAnimating = true

                                selectedPlayer = guess[0]

                                cardRotation.animateTo(
                                    targetValue = 180f,
                                    animationSpec = tween(700)
                                )

                                isGuessRevealed = true

                                isAnimating = false
                            }
                        }
                    }
                )

                GuessCard(
                    player = guess[1],
                    isSelected = selectedPlayer == guess[1],
                    rotation = if(selectedPlayer == guess[1]){
                        cardRotation.value
                    } else{
                        0f
                    },
                    guessingFinished = guessingFinished,
                    isAnimating = isAnimating,
                    onClick = {
                        if(selectedPlayer == null && !isAnimating){
                            scope.launch {
                                isAnimating = true

                                selectedPlayer = guess[1]

                                cardRotation.animateTo(
                                    targetValue = 180f,
                                    animationSpec = tween(700)
                                )

                                isGuessRevealed = true

                                isAnimating = false
                            }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(35.dp))

            GuessCard(
                player = guess[2],
                isSelected = selectedPlayer == guess[2],
                rotation = if(selectedPlayer == guess[2]){
                    cardRotation.value
                } else{
                    0f
                },
                guessingFinished = guessingFinished,
                isAnimating = isAnimating,
                onClick = {
                    if(selectedPlayer == null && !isAnimating){
                        scope.launch {
                            isAnimating = true

                            selectedPlayer = guess[2]

                            cardRotation.animateTo(
                                targetValue = 180f,
                                animationSpec = tween(700)
                            )

                            isGuessRevealed = true

                            isAnimating = false
                        }
                    }
                }
            )

            if (isGuessRevealed && selectedPlayer != null) {

                Spacer(modifier = Modifier.height(20.dp))

                if (isCorrectGuess) {

                    Text(
                        text = "CORRECT!",
                        color = YellowColor,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "YOU FOUND THE THIEF!",
                        color = WhiteText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                } else {

                    Text(
                        text = "WRONG!",
                        color = WhiteText,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "THE THIEF ESCAPED!",
                        color = WhiteText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            if (isGuessRevealed && selectedPlayer != null){

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        onContinue(isCorrectGuess)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = YellowColor
                    )
                ) {
                    Text(
                        text = "CONTINUE",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

    }
}

@Composable
private fun GuessCard(
    player: Player,
    isSelected: Boolean,
    rotation: Float,
    guessingFinished: Boolean,
    isAnimating: Boolean,
    onClick: () -> Unit
) {

    val cardImage = getRoleCard(player.role?.name ?: "Unknown")

    Box(
        modifier = Modifier
            .width(150.dp)
            .height(210.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clip(RoundedCornerShape(22.dp))
            .background(CardColor)
            .clickable(
                enabled = !guessingFinished && !isAnimating
            ) {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {

        if (isSelected && rotation > 90f) {

            androidx.compose.foundation.Image(
                painter = painterResource(
                    id = cardImage
                ),
                contentDescription = "Role card",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        rotationY = 180f
                    }
            )

        } else {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

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

private fun getRoleCard(roleName : String) : Int{ // Int -> Reference ID to the Image
    return when(roleName.uppercase()){ // Switch
        "KING" -> R.drawable.king_card
        "PRINCE" -> R.drawable.prince_card
        "KNIGHT" -> R.drawable.knight_card
        "THIEF" -> R.drawable.thief_card
        else -> R.drawable.card_back
    }
}

/*

 */