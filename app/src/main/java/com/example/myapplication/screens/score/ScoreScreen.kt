package com.example.myapplication.screens.score

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.model.Player
import com.example.myapplication.model.Role

private val BackgroundColor = Color(0xFF6E151F)
private val CardColor = Color(0xFF160A21)
private val YellowColor = Color(0xFFFFE000)
private val WhiteText = Color(0xFFF5F0F7)
private val GrayText = Color(0xFFB8AEBE)
private val PurpleBorder = Color(0xFF4B3457)

@Composable
fun ScoreScreen(
    players: List<Player>,
    roundScores: Map<String, Int>,
    totalScores: Map<String, Int>,
    currentRound: Int,
    isCorrectGuess: Boolean,
    onPlayAgain: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 28.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // TITLE
        Text(
            text = "THE HIDDEN THIEF",
            color = YellowColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        // ROUND
        Text(
            text = "ROUND $currentRound RESULTS",
            color = WhiteText,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(14.dp))

        // GUESS RESULT
        Text(
            text = if (isCorrectGuess)
                "✓  CORRECT GUESS!"
            else
                "✕  WRONG GUESS!",
            color = YellowColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = if (isCorrectGuess)
                "The thief was found!"
            else
                "The thief escaped!",
            color = GrayText,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(24.dp))


        // PLAYER SCORE CARDS
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            players.forEach { player ->

                val roundScore = roundScores[player.name] ?: 0
                val totalScore = totalScores[player.name] ?: 0

                ScoreCard(
                    player = player,
                    roundScore = roundScore,
                    totalScore = totalScore
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))


        // NEXT ROUND BUTTON
        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = YellowColor,
                contentColor = CardColor
            )
        ) {

            Text(
                text = "NEXT ROUND",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
private fun ScoreCard(
    player: Player,
    roundScore: Int,
    totalScore: Int
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(CardColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // PLAYER NAME + ROLE
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = player.name.uppercase(),
                    color = WhiteText,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = player.role?.name ?: "UNKNOWN",
                    color = YellowColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            // SCORES
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "ROUND SCORE",
                        color = GrayText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "$roundScore",
                        color = WhiteText,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "TOTAL SCORE",
                        color = GrayText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "$totalScore",
                        color = YellowColor,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}