// Este archivo contiene la estructura y funcionalidades UI de la pantalla de feedback de usuario
package com.example.paradiseresorts.ui.screens.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.domain.models.Feedback
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton
import kotlinx.coroutines.launch

@Composable
fun FeedbackScreen(feedbackViewModel: FeedbackViewModel) {
    val uiState by remember { derivedStateOf { feedbackViewModel.uiState } }
    val coroutineScope = rememberCoroutineScope()

    var opinionText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(3) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Formulario de opinión
        item {
            Text(
                text = "Bienvenido a la pantalla de Feedback",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(text = "Tu opinión", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = opinionText,
                onValueChange = { opinionText = it },
                placeholder = { Text("Escribe tu opinión...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Text(text = "Puntúa el servicio", fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                for (i in 1..5) {
                    Button(
                        onClick = { rating = i },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (i == rating) Color(0xFF4CAF50) else Color.LightGray
                        ),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(40.dp)
                    ) {
                        Text(text = i.toString(), color = Color.White)
                    }
                }
            }

            PrimaryButton(
                text = "Enviar Opinión",
                onClick = {
                    coroutineScope.launch {
                        if (opinionText.isNotBlank()) {
                            feedbackViewModel.addFeedbackForCurrentUser(
                                opinion = opinionText,
                                rate = rating
                            )
                            opinionText = ""
                            rating = 3
                        }
                    }
                },
                enabled = opinionText.isNotBlank()
            )

            if (uiState.feedbackMessage.isNotBlank()) {
                Text(
                    text = uiState.feedbackMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Opiniones de usuarios",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Lista de opiniones
        items(uiState.feedbacks) { feedback ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFCAF45)),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "${feedback.name} ${feedback.lastName}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text("Puntaje: ${feedback.rate}", modifier = Modifier.padding(top = 4.dp))
                    Text(feedback.opinion, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}


/*
@Composable
fun FeedbackScreen(feedbackViewModel: FeedbackViewModel) {
    val uiState by remember { derivedStateOf { feedbackViewModel.uiState } }
    val coroutineScope = rememberCoroutineScope()

    var opinionText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(3) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Formulario de opinión
        item {
            Text(
                text = "Bienvenido a la pantalla de Feedback",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(text = "Tu opinión", fontWeight = FontWeight.SemiBold)
            OutlinedTextField(
                value = opinionText,
                onValueChange = { opinionText = it },
                placeholder = { Text("Escribe tu opinión...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            Text(text = "Puntúa el servicio", fontWeight = FontWeight.SemiBold)
            Row(modifier = Modifier.padding(vertical = 8.dp)) {
                for (i in 1..5) {
                    Button(
                        onClick = { rating = i },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (i == rating) Color(0xFF4CAF50) else Color.LightGray
                        ),
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(40.dp)
                    ) {
                        Text(text = i.toString(), color = Color.White)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            PrimaryButton(
                text = "Enviar Opinión",
                onClick = {
                    coroutineScope.launch {
                        if (opinionText.isNotBlank()) {
                            feedbackViewModel.addFeedbackForCurrentUser(opinionText, rating)
                            opinionText = ""
                            rating = 3
                        }
                    }
                }
            )

            if (uiState.feedbackMessage.isNotBlank()) {
                Text(
                    text = uiState.feedbackMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Opiniones de usuarios",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Lista de opiniones
        items(uiState.feedbacks) { feedback ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFCAF45))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("${feedback.name} ${feedback.lastName}", fontWeight = FontWeight.Bold)
                    Text("Puntaje: ${feedback.rate}")
                    Text(feedback.opinion, modifier = Modifier.padding(top = 4.dp))
                }
            }
        }
    }
}*/
