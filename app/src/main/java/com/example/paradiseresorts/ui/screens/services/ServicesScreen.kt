//Este archivo contiene la estructura y funcionalidad UI de la pantalla de servicios extras.

package com.example.paradiseresorts.ui.screens.services

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
fun ServicesScreen(
    servicesViewModel: ServicesViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Bienvenido a la pantalla de Servicios", fontSize = 32.sp)

        //Elementos de relleno para comprobar la pantalla scrollable
        repeat(40) { index ->
            Text("Elemento #$index", modifier = Modifier.padding(4.dp))
        }
    }
}