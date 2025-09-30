//Este archivo contiene la definición del ViewModel respectivo de la pantalla de reservaciones.
package com.example.paradiseresorts.ui.screens.reservation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.RoomRepository
import com.example.paradiseresorts.domain.models.Reservation
import com.example.paradiseresorts.ui.classes.ReservationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ReservationViewModel(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    companion object {
        private const val TAG = "reservationVM"
    }

    var uiState: ReservationUiState by mutableStateOf(ReservationUiState())
        private set

    fun loadReservations(dui: String, today: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)

                val reservations = reservationRepository.getAllReservationByUserDUI(dui, today) ?: emptyList()

                uiState = uiState.copy(isLoading = false, reservations = reservations)
                Log.d(TAG, "Reservaciones cargadas")
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = e.message)
                Log.e(TAG, "Error cargando reservas", e)
            }
        }
    }

    fun createReservation(reservation: Reservation) {
        viewModelScope.launch {
            try {
                reservationRepository.createReservation(reservation)
                roomRepository.setRoomNotAvailable(reservation.roomId)
                Log.d(TAG, "Reservación creada correctamente")
            } catch (e: Exception) {
                Log.e(TAG, "Error creando reservación", e)
            }
        }
    }
}