package com.example.myapplication.others

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BgDark = Color(0xFF210407)
private val BgMid = Color(0xFF4A0A0F)
private val BgVignette = Color(0xFF6E1418)
private val TitleGold = Color(0xFFF3A93E)
private val GoldLight = Color(0xFFFCE8A6)
private val GoldMid = Color(0xFFE7A93B)
private val GoldDark = Color(0xFFA9691A)
private val CardBg = Color(0x47000000)
private val BodyText = Color(0xFFE8C9A0)
private val HeadingText = Color(0xFFFCE8A6)
private val PoliceColor = Color(0xFF5DCAA5)
private val ChorColor = Color(0xFFD4537E)
private val PlayText = Color(0xFF3A0A0A)

@Composable
fun RulesScreen(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(colors = listOf(BgVignette, BgMid, BgDark)))
            .background(
                Brush.verticalGradient(
                    colors = listOf(BgDark.copy(alpha = 0.35f), Color.Transparent, BgDark.copy(alpha = 0.5f))
                )
            )
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 22.dp, start = 18.dp)
                .size(40.dp)
                .background(Color.Black.copy(alpha = 0.25f), CircleShape)
        ) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = TitleGold)
        }

        Text(
            text = "HOW TO PLAY",
            color = TitleGold,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 1.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 28.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 90.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            RuleCard(number = "1", title = "Players & Roles") {
                Text(
                    text = "Each round, all 4 players are secretly given one hidden role.",
                    color = BodyText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    RoleChip("POLICE", PoliceColor, Modifier.weight(1f))
                    RoleChip("CHOR", ChorColor, Modifier.weight(1f))
                    RoleChip("CIVILIAN", GoldMid, Modifier.weight(1f))
                    RoleChip("CIVILIAN", GoldMid, Modifier.weight(1f))
                }
            }

            RuleCard(number = "2", title = "Reveal") {
                Text(
                    text = "Only you can see your own role card. Everyone else's card stays hidden until guessed or revealed.",
                    color = BodyText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            RuleCard(number = "3", title = "Police Guesses") {
                Text(
                    text = "The Police player must guess which of the 3 remaining players is the Chor.",
                    color = BodyText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            RuleCard(number = "4", title = "Scoring") {
                Text(
                    text = "Each role scores differently depending on the outcome:",
                    color = BodyText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                ScoreRow("POLICE guesses correctly", PoliceColor, "+800")
                Spacer(modifier = Modifier.height(8.dp))
                ScoreRow("CHOR escapes (Police guesses wrong)", ChorColor, "+800")
                Spacer(modifier = Modifier.height(8.dp))
                ScoreRow("Wrong-guessing role (Police or Chor)", GoldMid, "+0", valueColor = Color(0xFF8A8A8A))
                Spacer(modifier = Modifier.height(8.dp))
                ScoreRow("CIVILIAN (both, every round)", GoldMid, "+500")
            }

            RuleCard(number = "5", title = "Winning") {
                Text(
                    text = "Roles reshuffle each round. After all rounds, whoever has the highest total score on the scoreboard wins.",
                    color = BodyText,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            GotItButton(onClick = onBack)

            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
private fun RuleCard(number: String, title: String, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 14.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardBg)
            .padding(14.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(26.dp)
                    .clip(CircleShape)
                    .background(Brush.verticalGradient(listOf(GoldLight, GoldMid, GoldDark))),
                contentAlignment = Alignment.Center
            ) {
                Text(text = number, color = PlayText, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = title, color = HeadingText, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(modifier = Modifier.padding(start = 36.dp)) {
            Column { content() }
        }
    }
}

@Composable
private fun RoleChip(text: String, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ScoreRow(label: String, color: Color, points: String, valueColor: Color = HeadingText) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Text(text = points, color = valueColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun GotItButton(onClick: () -> Unit) {
    val ribbonShape = GenericShape { size, _ ->
        val w = size.width
        val h = size.height
        val tip = h * 0.55f
        moveTo(tip, 0f)
        lineTo(w - tip, 0f)
        lineTo(w, h / 2f)
        lineTo(w - tip, h)
        lineTo(tip, h)
        lineTo(0f, h / 2f)
        close()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(58.dp)
            .clip(ribbonShape)
            .background(Brush.verticalGradient(listOf(GoldLight, GoldMid, GoldDark)))
            .padding(4.dp)
            .clip(ribbonShape)
            .background(Brush.radialGradient(listOf(Color(0xFFC23A2A), Color(0xFF6E1512))))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "GOT IT", color = PlayText, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
    }
}