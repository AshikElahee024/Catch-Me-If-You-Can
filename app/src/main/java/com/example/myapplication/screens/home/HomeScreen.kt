package com.example.myapplication.screens.home

package com.example.chorpolicedakatbabu

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BgDark = Color(0xFF210407)
private val BgMid = Color(0xFF4A0A0F)
private val BgVignette = Color(0xFF6E1418)
private val TitleGold = Color(0xFFF3A93E)
private val ButtonGoldLight = Color(0xFFFCE8A6)
private val ButtonGoldMid = Color(0xFFE7A93B)
private val ButtonGoldDark = Color(0xFFA9691A)
private val ButtonRedLight = Color(0xFFC23A2A)
private val ButtonRedDark = Color(0xFF6E1512)
private val PlayText = Color(0xFF3A0A0A)

@Composable
fun HomeScreen(
    onPlayClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.radialGradient(colors = listOf(BgVignette, BgMid, BgDark)))
            .background(
                Brush.verticalGradient(
                    colors = listOf(BgDark.copy(alpha = 0.35f), Color.Transparent, BgDark.copy(alpha = 0.5f))
                )
            )
    ) {
        val screenHeight = maxHeight

        IconButton(
            onClick = onSettingsClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 20.dp, end = 16.dp)
                .size(52.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Settings",
                tint = TitleGold,
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = "THE HIDDEN\nTHIEF",
            color = TitleGold,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 38.sp,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight * 0.20f)
                .fillMaxWidth()
        )

        PlayButton(
            onClick = onPlayClick,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = screenHeight * 0.67f)
        )
    }
}

@Composable
private fun PlayButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
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
        modifier = modifier.fillMaxWidth(0.92f).height(66.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val midY = size.height / 2f
            drawLine(
                color = ButtonGoldMid,
                start = Offset(-size.width * 0.06f, midY),
                end = Offset(size.width * 0.06f, midY),
                strokeWidth = 5f
            )
            drawLine(
                color = ButtonGoldMid,
                start = Offset(size.width * 0.94f, midY),
                end = Offset(size.width * 1.06f, midY),
                strokeWidth = 5f
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(0.86f)
                .fillMaxSize()
                .clip(ribbonShape)
                .background(Brush.verticalGradient(listOf(ButtonGoldLight, ButtonGoldMid, ButtonGoldDark)))
                .padding(4.dp)
                .clip(ribbonShape)
                .background(Brush.radialGradient(listOf(ButtonRedLight, ButtonRedDark)))
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Flourish(mirrored = false, modifier = Modifier.align(Alignment.CenterStart).padding(start = 22.dp).size(20.dp))
            Text(text = "PLAY", color = PlayText, fontSize = 26.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            Flourish(mirrored = true, modifier = Modifier.align(Alignment.CenterEnd).padding(end = 22.dp).size(20.dp))
        }
    }
}

@Composable
private fun Flourish(mirrored: Boolean, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val dir = if (mirrored) -1f else 1f

        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(w * 0.5f, h * 0.1f)
            cubicTo(w * 0.5f + dir * w * 0.5f, h * 0.15f, w * 0.5f + dir * w * 0.5f, h * 0.55f, w * 0.5f, h * 0.5f)
            cubicTo(w * 0.5f - dir * w * 0.15f, h * 0.45f, w * 0.5f + dir * w * 0.35f, h * 0.75f, w * 0.5f, h * 0.9f)
        }

        drawPath(path = path, color = TitleGold, style = Stroke(width = 2.5f))
    }
}