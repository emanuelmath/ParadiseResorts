package com.example.paradiseresorts.ui.screens.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.ui.components.AppColors
import com.example.paradiseresorts.ui.theme.LocalAppColors
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton

@Composable
fun StartScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    val appColors = LocalAppColors.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(colors = appColors.backgroundGradient))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Paradise Resorts",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive
                    )
                )

                Spacer(modifier = Modifier.fillMaxWidth().height(height = 48.dp))

                PrimaryButton(
                    text = "Iniciar sesión",
                    onClick = onLoginClick,
                    enabled = true
                )

                Spacer(modifier = Modifier.fillMaxWidth().height(height = 48.dp))

                PrimaryButton(
                    text = "Registrarse",
                    onClick = onRegisterClick,
                    enabled = true
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun StartScreenPreview () {
    val fakeColors = AppColors()
    StartScreen(
        onLoginClick = {},
        onRegisterClick = {}
    )
}