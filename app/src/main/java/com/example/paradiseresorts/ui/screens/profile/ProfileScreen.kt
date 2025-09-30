//Este archivo contiene la estructura y funcionalidad UI de la pantalla de perfil de usuario
package com.example.paradiseresorts.ui.screens.profile

import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.ui.components.buttons.DangerButton
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton
import com.example.paradiseresorts.ui.components.dialogs.AddCardDialog
import com.example.paradiseresorts.ui.theme.LocalAppColors
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val uiState = profileViewModel.uiState
    val coroutineScope = rememberCoroutineScope()
    var showAddCardDialog by remember { mutableStateOf(false) }

    // 🔹 Cargar usuario al iniciar
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val session = profileViewModel.getCurrentSession()
            session?.dui?.let { dui ->
                profileViewModel.loadUser(dui)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TopBar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(Color(0xFFC13584))
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Perfil de usuario",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(48.dp))
        }

        // Card de presentación
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC13584)),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val user = uiState.userInProfile

                if (user != null) {
                    Text(
                        text = "${user.name} ${user.lastName}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Foto de perfil",
                        tint = Color.White,
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    UserInfoRow(label = "DUI", value = user.dui)
                    UserInfoRow(label = "Fecha de nacimiento", value = user.dateOfBirthday)
                    UserInfoRow(label = "Email", value = user.email)
                    UserInfoRow(label = "Teléfono", value = user.phoneNumber)
                } else {
                    Text(
                        text = "Cargando datos...",
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))



        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Tarjetas asociadas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                if (uiState.cards.isEmpty()) {
                    Text(
                        text = "No tienes ninguna tarjeta asociada",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                } else {
                    uiState.cards.forEach { card ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CreditCard,
                                    contentDescription = "Ícono de tarjeta",
                                    tint = Color(0xFF405DE6),
                                    modifier = Modifier.size(32.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = "Tarjeta Visa",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = card.code,
                                        fontSize = 14.sp,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Botón añadir tarjeta
            PrimaryButton(
                text = "Añadir tarjeta",
                onClick = { showAddCardDialog = true },
                enabled = uiState.cards.isEmpty()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón cerrar sesión
            DangerButton(
                text = if (uiState.isLoggingOut) "Cerrando..." else "Cerrar sesión",
                onClick = {
                    profileViewModel.logout { success ->
                        if (success) onLogoutClick()
                    }
                },
                enabled = !uiState.isLoggingOut
            )
        }
    }

    // Mostrar dialogo de añadir tarjeta
    if (showAddCardDialog) {
        AddCardDialog(
            onDismiss = { showAddCardDialog = false },
            onSave = { code, expiration, cvv ->
                profileViewModel.addCard(code, expiration, cvv)
                showAddCardDialog = false
            }
        )
    }
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, color = Color.White, fontWeight = FontWeight.Bold)
            Text(value, color = Color.White, fontSize = 16.sp)
        }
    }
}
