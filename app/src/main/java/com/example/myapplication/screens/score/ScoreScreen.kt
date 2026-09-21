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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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


private const val TOTAL_ROUNDS = 5
@Composable
fun ScoreScreen(
    players: List<Player>,
    roundScores: Map<String, Int>,
    totalScores: Map<String, Int>,
    currentRound: Int,
    isCorrectGuess: Boolean,
    onPlayAgain: () -> Unit
) {
    val isFinalRound = currentRound == TOTAL_ROUNDS

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .verticalScroll(rememberScrollState())
            .padding(
                start = 20.dp,
                end = 20.dp,
                top = 28.dp,
                bottom = 20.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isFinalRound)
                "FINAL RESULTS"
            else
                "ROUND $currentRound RESULTS",
            color = WhiteText,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        if (!isFinalRound) {

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

        } else {

            Text(
                text = "10 ROUNDS COMPLETE!",
                color = YellowColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Final scores are in!",
                color = GrayText,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

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

        if (isFinalRound) {

            val winner = getWinner(
                players = players,
                totalScores = totalScores
            )

            if (winner != null) {

                Spacer(modifier = Modifier.height(20.dp))

                WinnerCard(
                    winner = winner,
                    totalScore = totalScores[winner.name] ?: 0
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!isFinalRound) {

            Button(
                onClick = onPlayAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
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

        } else {

            Text(
                text = "GAME OVER",
                color = YellowColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(5.dp))
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = player.name.uppercase(),
                    color = WhiteText,
                    fontSize = 15.sp,
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

            Spacer(modifier = Modifier.height(8.dp))

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
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "$roundScore",
                        color = WhiteText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }


                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Text(
                        text = "TOTAL SCORE",
                        color = GrayText,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "$totalScore",
                        color = YellowColor,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

private fun getWinner(
    players: List<Player>,
    totalScores: Map<String, Int>
): Player? {
    return players.maxByOrNull { player ->
        totalScores[player.name] ?: 0
    }
}

@Composable
private fun WinnerCard(
    winner: Player,
    totalScore: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(CardColor)
            .padding(20.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "WINNER",
                color = YellowColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = winner.name.uppercase(),
                color = WhiteText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "$totalScore POINTS",
                color = YellowColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}