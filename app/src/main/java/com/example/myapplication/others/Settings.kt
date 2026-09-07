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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material.icons.filled.MenuBook
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
private val CardBg = Color(0x3DFFFFFF)
private val CardBorder = Color(0x33E7A93B)
private val HeadingText = Color(0xFFFCE8A6)
private val SubText = Color(0xFFC89A6A)
private val PlayText = Color(0xFF3A0A0A)

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
                .padding(top = 96.dp, start = 20.dp, end = 20.dp, bottom = 24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SectionLabel("Sound")

            var musicOn by remember { mutableStateOf(true) }
            var musicVolume by remember { mutableFloatStateOf(0.7f) }
            SettingsCard {
                IconRow(
                    icon = Icons.Filled.MusicNote,
                    label = "Background Music",
                    trailing = {
                        GoldSwitch(checked = musicOn, onCheckedChange = { musicOn = it })
                    }
                )
                if (musicOn) {
                    Spacer(modifier = Modifier.height(4.dp))
                    GoldSlider(value = musicVolume, onValueChange = { musicVolume = it })
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            var sfxOn by remember { mutableStateOf(true) }
            var sfxVolume by remember { mutableFloatStateOf(0.85f) }
            SettingsCard {
                IconRow(
                    icon = Icons.Filled.VolumeUp,
                    label = "Sound Effects",
                    trailing = {
                        GoldSwitch(checked = sfxOn, onCheckedChange = { sfxOn = it })
                    }
                )
                if (sfxOn) {
                    Spacer(modifier = Modifier.height(4.dp))
                    GoldSlider(value = sfxVolume, onValueChange = { sfxVolume = it })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionLabel("Gameplay")

            var vibrationOn by remember { mutableStateOf(false) }
            SettingsCard {
                IconRow(
                    icon = Icons.Filled.Vibration,
                    label = "Vibration",
                    subLabel = "Buzz on role reveal & guess result",
                    trailing = {
                        GoldSwitch(checked = vibrationOn, onCheckedChange = { vibrationOn = it })
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            SectionLabel("Help & About")

            SettingsCard {
                NavRow(
                    icon = Icons.Filled.MenuBook,
                    label = "How to Play",
                    onClick = onRulesClick
                )
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))
                IconRow(
                    icon = Icons.Filled.Info,
                    label = "Version",
                    trailing = {
                        Text(text = "1.0.0", color = GoldMid, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                )
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
        letterSpacing = 1.2.sp,
        modifier = Modifier.padding(start = 4.dp, bottom = 10.dp)
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(CardBg)
            .padding(16.dp)
    ) {
        content()
    }
}

@Composable
private fun IconBadge(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
        modifier = Modifier
            .size(38.dp)
            .clip(CircleShape)
            .background(Brush.verticalGradient(listOf(GoldMid.copy(alpha = 0.28f), GoldDark.copy(alpha = 0.18f)))),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = GoldLight, modifier = Modifier.size(19.dp))
    }
}

@Composable
private fun IconRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    subLabel: String? = null,
    trailing: @Composable () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBadge(icon)
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, color = HeadingText, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            if (subLabel != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subLabel, color = SubText, fontSize = 11.sp)
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        trailing()
    }
}

@Composable
private fun NavRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconBadge(icon)
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, color = HeadingText, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
        Icon(imageVector = Icons.Filled.ChevronRight, contentDescription = null, tint = SubText, modifier = Modifier.size(20.dp))
    }
}

@Composable
private fun GoldSwitch(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
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

@Composable
private fun GoldSlider(value: Float, onValueChange: (Float) -> Unit) {
    Slider(
        value = value,
        onValueChange = onValueChange,
        colors = SliderDefaults.colors(
            thumbColor = GoldLight,
            activeTrackColor = GoldMid,
            inactiveTrackColor = Color.Black.copy(alpha = 0.3f)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 50.dp)
    )
}

@Composable
private fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(CardBorder)
    )
}