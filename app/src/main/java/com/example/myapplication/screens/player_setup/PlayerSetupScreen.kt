package com.example.myapplication.screens.player_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.model.Player


private val BackgroundColor = Color(0xFF6E1418)
private val CardColor = Color(0xFF160A21)
private val FieldColor = Color(0xFF100719)
private val YellowColor = Color(0xFFFFE000)
private val PurpleBorder = Color(0xFF4B3457)
private val WhiteText = Color(0xFFF5F0F7)
private val GrayText = Color(0xFFB8AEBE)


@Composable
fun PlayerSetupScreen(
    onStartGame: (List<Player>) -> Unit,
    onRulesClick: () -> Unit
) {

    var player1 by remember { mutableStateOf("") }
    var player2 by remember { mutableStateOf("") }
    var player3 by remember { mutableStateOf("") }
    var player4 by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = 28.dp,
                    bottom = 18.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF34203F)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👑",
                    fontSize = 28.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))


            // ---------- TITLE ----------

            Text(
                text = "THE HIDDEN THIEF",
                color = YellowColor,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "King • Queen • Thief • Knight",
                color = GrayText,
                fontSize = 12.sp
            )


            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(CardColor)
                    .padding(
                        start = 14.dp,
                        end = 14.dp,
                        top = 14.dp,
                        bottom = 18.dp
                    )
            ) {

                // Card title

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = YellowColor,
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(modifier = Modifier.size(7.dp))

                    Text(
                        text = "Enter 4 Player Names",
                        color = WhiteText,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                PlayerNameField(
                    playerName = player1,
                    onNameChange = { player1 = it },
                    label = "Player 1"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PlayerNameField(
                    playerName = player2,
                    onNameChange = { player2 = it },
                    label = "Player 2"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PlayerNameField(
                    playerName = player3,
                    onNameChange = { player3 = it },
                    label = "Player 3"
                )

                Spacer(modifier = Modifier.height(10.dp))

                PlayerNameField(
                    playerName = player4,
                    onNameChange = { player4 = it },
                    label = "Player 4"
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {

                    if (
                        player1.isNotBlank() &&
                        player2.isNotBlank() &&
                        player3.isNotBlank() &&
                        player4.isNotBlank()
                    ) {

                        val players = listOf(
                            Player(player1.trim()),
                            Player(player2.trim()),
                            Player(player3.trim()),
                            Player(player4.trim())
                        )

                        onStartGame(players)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = YellowColor,
                    contentColor = Color.Black
                )
            ) {

                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.size(4.dp))

                Text(
                    text = "START GAME",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    onRulesClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    PurpleBorder
                ),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(
                    contentColor = YellowColor,
                    containerColor = FieldColor
                )
            ) {

                Icon(
                    imageVector = Icons.Default.HelpOutline,
                    contentDescription = null,
                    modifier = Modifier.size(17.dp)
                )

                Spacer(modifier = Modifier.size(5.dp))

                Text(
                    text = "How To Play & Rules",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
private fun PlayerNameField(
    playerName: String,
    onNameChange: (String) -> Unit,
    label: String
) {

    OutlinedTextField(
        value = playerName,
        onValueChange = onNameChange,

        label = {
            Text(
                text = label,
                color = GrayText,
                fontSize = 13.sp
            )
        },

        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                tint = YellowColor,
                modifier = Modifier.size(19.dp)
            )
        },

        singleLine = true,

        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),

        modifier = Modifier.fillMaxWidth().height(60.dp),

        shape = RoundedCornerShape(10.dp),

        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = WhiteText,
            unfocusedTextColor = WhiteText,

            focusedContainerColor = FieldColor,
            unfocusedContainerColor = FieldColor,

            focusedBorderColor = YellowColor,
            unfocusedBorderColor = PurpleBorder,

            cursorColor = YellowColor,

            focusedLeadingIconColor = YellowColor,
            unfocusedLeadingIconColor = YellowColor
        )
    )
}
