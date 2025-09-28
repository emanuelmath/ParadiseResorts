package com.example.paradiseresorts.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.paradiseresorts.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        Text(
            text = "Bienvenido a pantalla HOME"
        )
        Spacer(modifier = Modifier.size(10.dp))
        Button(onClick = {
            navController.navigate(Screen.Start.route) {
                popUpTo(Screen.Home.route) { inclusive = true }
            }
        }) { Text("Cerrar Sesión") }
    }
}