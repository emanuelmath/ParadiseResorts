//Este archivo contiene la estructura y funcionalidad UI de la pantalla de servicios extras.

package com.example.paradiseresorts.ui.screens.services

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paradiseresorts.R
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton
import com.example.paradiseresorts.ui.screens.reservation.ReservationViewModel
import com.example.paradiseresorts.ui.viewmodels.UserSessionViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ServicesScreen(
    servicesViewModel: ServicesViewModel,
    reservationViewModel: ReservationViewModel,
    userSessionViewModel: UserSessionViewModel
) {
    val uiState = servicesViewModel.uiState
    val reservationState = reservationViewModel.uiState
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val userDui = userSessionViewModel.duiState.dui

    LaunchedEffect(userDui) {
        if (userDui != null) {
            servicesViewModel.loadCatalogServices()
            reservationViewModel.loadReservations(userDui, getTodayDate())
        }
    }

    // 🎨 Paleta de colores
    val blueColor = Color(0xFF405DE6)
    val purpleColor = Color(0xFF5851DB)
    val orangeColor = Color(0xFFE1306C)
    val yellowColor = Color(0xFFFCAF45)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Servicios Extras",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = purpleColor,
                modifier = Modifier.padding(16.dp)
            )

            if (userDui == null) {
                Text(
                    text = "Cargando sesión...",
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                uiState.offeredServices.forEach { service ->
                    val serviceImage = when (service.nombre) {
                        "Spa Relax" -> R.drawable.spa
                        "Transporte VIP" -> R.drawable.transporte
                        "Tour de la ciudad" -> R.drawable.tour
                        "Comida buffet" -> R.drawable.buffet2
                        else -> null
                    }

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = blueColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Nombre del servicio
                            Text(
                                text = service.nombre,
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = yellowColor
                                )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Imagen si existe
                            serviceImage?.let { img ->
                                Image(
                                    painter = painterResource(id = img),
                                    contentDescription = service.nombre,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Precio
                            Text(
                                text = "$${"%,.2f".format(service.price)}",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFF8E21C)
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Botón
                            PrimaryButton(
                                text = "Adquirir servicio",
                                onClick = {
                                    if (reservationViewModel.reservationsHistory.isNotEmpty()) {
                                        // Actualizar saldo primero
                                        servicesViewModel.updateUserBalance(userDui, service.price) { success, msg ->
                                            scope.launch {
                                                if (!success) {
                                                    // Mostrar mensaje si el saldo es insuficiente
                                                    snackbarHostState.showSnackbar(msg)
                                                } else {
                                                    // Si hay saldo suficiente, adquirir el servicio
                                                    servicesViewModel.acquireService(service, userDui)
                                                    snackbarHostState.showSnackbar("Has adquirido el servicio: ${service.nombre}")
                                                }
                                            }
                                        }
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Necesitas tener una reservación activa para contratar servicios"
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun getTodayDate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(Date())
}
