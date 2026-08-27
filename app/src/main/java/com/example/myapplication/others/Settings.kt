package com.example.myapplication.others



import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
private val HeadingText = Color(0xFFFCE8A6)
private val SubText = Color(0xFFC89A6A)
private val PlayText = Color(0xFF3A0A0A)
private val DangerText = Color(0xFFD4537E)

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onRulesClick: () -> Unit
) {
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
            text = "SETTINGS",
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
            SectionLabel("Sound")
            SettingsCard {
                var musicOn by remember { mutableStateOf(true) }
                var musicVolume by remember { mutableFloatStateOf(0.7f) }
                var sfxOn by remember { mutableStateOf(true) }
                var sfxVolume by remember { mutableFloatStateOf(0.85f) }

                ToggleRow(label = "Background Music", checked = musicOn, onCheckedChange = { musicOn = it })
                GoldSlider(value = musicVolume, onValueChange = { musicVolume = it })

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))

                ToggleRow(label = "Sound Effects", checked = sfxOn, onCheckedChange = { sfxOn = it })
                GoldSlider(value = sfxVolume, onValueChange = { sfxVolume = it })
            }

            SectionLabel("Gameplay")
            SettingsCard {
                var vibrationOn by remember { mutableStateOf(false) }
                var timerOn by remember { mutableStateOf(true) }

                ToggleRow(
                    label = "Vibration",
                    subLabel = "Buzz on role reveal & guess result",
                    checked = vibrationOn,
                    onCheckedChange = { vibrationOn = it }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(16.dp))
                ToggleRow(
                    label = "Round Timer",
                    subLabel = "Auto-move to next round after guess",
                    checked = timerOn,
                    onCheckedChange = { timerOn = it }
                )
            }

            SectionLabel("Language")
            SettingsCard {
                var isBangla by remember { mutableStateOf(true) }
                LanguageSegmented(isBangla = isBangla, onChange = { isBangla = it })
            }

            SectionLabel("Players")
            ActionCard(text = "Edit Player Names", onClick = { })
            ActionCard(text = "Reset Scoreboard", onClick = { }, danger = true)

            SectionLabel("Help")
            ActionCard(text = "How to Play", onClick = onRulesClick)

            SectionLabel("About")
            SettingsCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    Text(text = "Version", color = SubText, fontSize = 12.sp)
                    Text(text = "1.0.0", color = GoldMid, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        color = GoldMid,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp, top = 4.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardBg)
            .padding(14.dp)
    ) {
        content()
    }
}

@Composable
private fun ActionCard(text: String, onClick: () -> Unit, danger: Boolean = false) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(CardBg)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (danger) DangerText else HeadingText,
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ToggleRow(
    label: String,
    subLabel: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = label, color = HeadingText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            if (subLabel != null) {
                Text(text = subLabel, color = SubText, fontSize = 11.sp)
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = PlayText,
                checkedTrackColor = GoldMid,
                checkedBorderColor = GoldDark,
                uncheckedThumbColor = GoldLight,
                uncheckedTrackColor = Color.Black.copy(alpha = 0.35f),
                uncheckedBorderColor = GoldDark
            )
        )
    }
}

@Composable
private fun GoldSlider(value: Float, onValueChange: (Float) -> Unit) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        colors = SliderDefaults.colors(
            thumbColor = GoldLight,
            activeTrackColor = GoldMid,
            inactiveTrackColor = Color.Black.copy(alpha = 0.35f)
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun LanguageSegmented(isBangla: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {
        SegmentButton(
            text = "\u09AC\u09BE\u0982\u09B2\u09BE",
            active = isBangla,
            modifier = Modifier.weight(1f),
            onClick = { onChange(true) }
        )
        SegmentButton(
            text = "English",
            active = !isBangla,
            modifier = Modifier.weight(1f),
            onClick = { onChange(false) }
        )
    }
}

@Composable
private fun SegmentButton(text: String, active: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .background(
                if (active) Brush.verticalGradient(listOf(GoldMid, GoldDark))
                else Brush.verticalGradient(listOf(Color.Transparent, Color.Transparent))
            )
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (active) PlayText else SubText,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(GoldDark.copy(alpha = 0.35f))
    )
}