//Este archivo contiene la definición del ViewModel respectivo de la pantalla de reservaciones.
package com.example.paradiseresorts.ui.screens.reservation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.ui.classes.ReservationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReservationViewModel(/*Introducir repositorios necesarios*/) : ViewModel() {

    //Etiqueta para filtar logs referentes a este VM:
    companion object {
        private const val TAG = "reservationVM"
    }

    var uiState: ReservationUiState by mutableStateOf(ReservationUiState())
        private set

    // Función básica representativa
    fun loadReservations() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000) // Simula carga
            uiState = uiState.copy(
                isLoading = false,
                message = "Reservaciones cargadas exitosamente"
            )
            Log.d(TAG, "Reservaciones cargadas")
        }
    }
}