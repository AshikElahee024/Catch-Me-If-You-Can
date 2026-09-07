package com.example.myapplication.screens.score

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
import com.example.myapplication.model.Role

private val BackgroundColor = Color(0xFF6E151F)
private val CardColor = Color(0xFF160A21)
private val YellowColor = Color(0xFFFFE000)
private val WhiteText = Color(0xFFF5F0F7)
private val GrayText = Color(0xFFB8AEBE)


@Composable
fun ScoreScreen(
    players: List<Player>,
    roundScores: Map<String, Int>,
    totalScores: Map<String, Int>,
    isCorrectGuess: Boolean,
    onPlayAgain: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "GAME OVER",
            color = WhiteText,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = if (isCorrectGuess)
                "CORRECT GUESS!"
            else
                "WRONG GUESS!",
            color = YellowColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(30.dp))


        players.forEach { player ->

            val roundScore = roundScores[player.name] ?: 0
            val totalScore = totalScores[player.name] ?: 0

            Text(
                text = "${player.name}    Round: $roundScore    Total: $totalScore",
                color = WhiteText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(15.dp))
        }


        Spacer(modifier = Modifier.weight(1f))


        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "PLAY AGAIN",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}