package com.example.paradiseresorts.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ProfileScreen(
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ) {
        Button(
            onClick = {
                onLogoutClick
            }
        ) {
            Text("Cerrar sesión")
        }
    }
}