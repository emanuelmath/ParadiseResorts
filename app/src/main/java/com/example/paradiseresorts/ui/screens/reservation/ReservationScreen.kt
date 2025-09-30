//Este archivo contiene la estructura y funcionalidades UI de la pantalla de reservaciones.

package com.example.paradiseresorts.ui.screens.reservation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.paradiseresorts.ui.components.buttons.PrimaryButton
import com.example.paradiseresorts.ui.components.buttons.SecondaryButton
import com.example.paradiseresorts.ui.viewmodels.UserSessionViewModel
import kotlinx.coroutines.launch

@Composable
fun ReservationScreen(
    userSessionViewModel: UserSessionViewModel,
    reservationViewModel: ReservationViewModel = viewModel(factory = ReservationViewModelFactory())
) {
    val uiState = reservationViewModel.uiState
    val dui = userSessionViewModel.dui
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(dui) {
        if (dui != null) {
            reservationViewModel.loadHotelsAndRooms()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Reservaciones",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF673AB7)
        )

        if (dui == null) {
            Text("Cargando sesión de usuario...", color = Color.Gray)
            return@Column
        }

        // Habitaciones dentro de Cards
        Text(
            "Selecciona una habitación",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = Color(0xFF3F51B5)
        )
        uiState.rooms.forEach { room ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { reservationViewModel.selectRoom(room) }
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFC107)),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.selectedRoom == room,
                        onClick = { reservationViewModel.selectRoom(room) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("${room.name} - ${room.hotelId}", color = Color.Black, fontWeight = FontWeight.Bold)
                        Text("Precio: \$${room.price}", color = Color.Black)
                    }
                }
            }
        }

        // Total
        Text(
            "Total: \$${"%,.2f".format(uiState.total)}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color(0xFF3F51B5)
        )

        // Mensajes
        uiState.message?.let { Text(it, color = Color.Green, fontWeight = FontWeight.Bold) }
        uiState.error?.let { Text(it, color = Color.Red, fontWeight = FontWeight.Bold) }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PrimaryButton(
                text = "Confirmar",
                onClick = {
                    dui?.let { currentDui ->
                        scope.launch {
                            // Primero intentamos actualizar el balance
                            reservationViewModel.updateUserBalance(currentDui, uiState.total) { success, msg ->
                                scope.launch {
                                    if (!success) {
                                        // Mostrar snackbar si el saldo es insuficiente
                                        snackbarHostState.showSnackbar(msg)
                                    } else {
                                        // Si hay saldo suficiente, crear reservación
                                        reservationViewModel.confirmReservation(currentDui) { successRes, msgRes ->
                                            scope.launch {
                                                snackbarHostState.showSnackbar(msgRes)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                enabled = uiState.selectedRoom != null
            )

            SecondaryButton(
                text = "Cancelar",
                onClick = { reservationViewModel.cancelSelection() },
                enabled = true
            )
        }
    }
}


/*
@Composable
fun ReservationScreen(
    userSessionViewModel: UserSessionViewModel,
    reservationViewModel: ReservationViewModel = viewModel(factory = ReservationViewModelFactory())
) {
    val uiState = reservationViewModel.uiState
    val dui = userSessionViewModel.dui
    val scope = rememberCoroutineScope()

    val colors = listOf(
        Color(0xFF405DE6),
        Color(0xFF5851DB),
        Color(0xFFC13584),
        Color(0xFFE1306C),
        Color(0xFFFCAF45)
    )

    LaunchedEffect(dui) {
        if (dui != null) {
            reservationViewModel.loadHotelsAndRooms()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Reservaciones",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = colors[0]
        )

        if (dui == null) {
            Text("Cargando sesión de usuario...", color = Color.Gray)
            return@Column
        }

        // Hoteles
        Text(
            "Selecciona un hotel",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = colors[1]
        )
        uiState.hotels.forEachIndexed { index, hotel ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { reservationViewModel.selectHotel(hotel) }
                    .padding(4.dp)
            ) {
                Checkbox(
                    checked = uiState.selectedHotel == hotel,
                    onCheckedChange = { reservationViewModel.selectHotel(hotel) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colors[index % colors.size],
                        uncheckedColor = Color.Gray
                    )
                )
                Text(
                    "${hotel.name} - ${hotel.location}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Black
                )
            }
        }

        // Habitaciones
        Text(
            "Selecciona una habitación",
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            color = colors[2]
        )
        uiState.rooms.forEachIndexed { index, room ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { reservationViewModel.selectRoom(room) }
                    .padding(4.dp)
            ) {
                Checkbox(
                    checked = uiState.selectedRoom == room,
                    onCheckedChange = { reservationViewModel.selectRoom(room) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = colors[(index + 1) % colors.size],
                        uncheckedColor = Color.Gray
                    )
                )
                Text(
                    "${room.name} - \$${room.price}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.Black
                )
            }
        }

        // Total
        Text(
            "Total: \$${"%,.2f".format(uiState.total)}",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colors[3]
        )

        // Mensajes
        uiState.message?.let { Text(it, color = Color.Green, fontWeight = FontWeight.Bold) }
        uiState.error?.let { Text(it, color = Color.Red, fontWeight = FontWeight.Bold) }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            PrimaryButton(
                text = "Confirmar",
                onClick = {
                    dui?.let { currentDui ->
                        reservationViewModel.confirmReservation(currentDui) { _, msg ->
                            scope.launch { Log.d("ReservationScreen", msg) }
                        }
                    }
                },
                enabled = uiState.selectedHotel != null && uiState.selectedRoom != null
            )

            SecondaryButton(
                text = "Cancelar",
                onClick = { reservationViewModel.cancelSelection() },
                enabled = true
            )
        }
    }
}
*/
