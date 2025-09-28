// Este archivo contiene la estructura y funcionalidades UI de la pantalla de feedback de usuario
package com.example.paradiseresorts.ui.screens.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FeedbackScreen(
    feedbackViewModel: FeedbackViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Bienvenido a la pantalla de Feedback", fontSize = 32.sp)

        //Elementos de relleno para comprobar la pantalla scrollable
        repeat(40) { index ->
            Text("Elemento #$index", modifier = Modifier.padding(4.dp))
        }
    }
}