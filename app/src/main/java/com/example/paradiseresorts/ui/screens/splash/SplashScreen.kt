//Este archivo contiene la estructura composable y funcionalidad de la pantalla de arranque
package com.example.paradiseresorts.ui.screens.splash

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import com.example.paradiseresorts.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.paradiseresorts.ui.navigation.Screen
import com.example.paradiseresorts.ui.theme.LocalAppColors
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel,
    onSessionActive: () -> Unit,
    onSessionRequired: () -> Unit,
    navController: NavHostController,
    splashTime: Long = 3000L
) {
    val appColors = LocalAppColors.current
    val alpha = remember { Animatable(initialValue = 0f) }
    val uiState = splashViewModel.uiState

    //Controlador para la animación 'fade in'
    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(brush = Brush.linearGradient(colors = appColors.backgroundGradient))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icono_palmera),
                    contentDescription = "Icono de palmera",
                    modifier = Modifier
                        .size(size = 120.dp)
                        .graphicsLayer(alpha = alpha.value) //Aplicación del efecto fade in
                )

                Text(
                    text = "Paradise Resorts",
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 48.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Cursive
                    ),
                    modifier = Modifier
                        .graphicsLayer(alpha = alpha.value) //Aplicación del efecto fade in
                )

                Spacer(modifier = Modifier.height(height = 48.dp))

                CircularProgressIndicator(
                    color = Color.White,
                    strokeWidth = 5.dp,
                    modifier = Modifier.size(size = 56.dp)
                )
            }
        }
    }

    //Navegación hacia la siguiente pantalla:
    LaunchedEffect(Unit) {
        delay(splashTime)
        splashViewModel.checkSession { hasSession ->
            if (!hasSession) {
                onSessionRequired()
            }
        }
    }

    LaunchedEffect(uiState.isSessionActive) {
        if (uiState.isSessionActive == true) {
            Log.d("DUI", "Valor de dui: ${uiState.duiSession}")
            onSessionActive()
        }
    }
}
