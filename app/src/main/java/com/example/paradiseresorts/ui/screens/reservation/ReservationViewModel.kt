//Este archivo contiene la definición del ViewModel respectivo de la pantalla de reservaciones.
package com.example.paradiseresorts.ui.screens.reservation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.HotelRepository
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.RoomRepository
import com.example.paradiseresorts.data.repository.TransactionRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.domain.models.Hotel
import com.example.paradiseresorts.domain.models.Reservation
import com.example.paradiseresorts.domain.models.Room
import com.example.paradiseresorts.domain.models.Transaction
import com.example.paradiseresorts.ui.classes.ReservationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.paradiseresorts.ui.classes.ReservationFormUiState
import java.time.LocalDate

class ReservationViewModel(
    private val reservationRepository: ReservationRepository,
    private val transactionRepository: TransactionRepository,
    private val roomRepository: RoomRepository,
    private val hotelRepository: HotelRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    var uiState by mutableStateOf(ReservationFormUiState())
        private set

    var reservationsHistory by mutableStateOf<List<Reservation>>(emptyList())
        private set

    var reservations by mutableStateOf<List<Reservation>>(emptyList())
        private set

    fun loadReservations(dui: String, today: String) {
        viewModelScope.launch {
            try {
                reservationsHistory = reservationRepository.getAllReservationByUserDUI(dui, today) ?: emptyList()
            } catch (e: Exception) {
                Log.e("ReservationVM", "Error cargando reservaciones: ${e.message}")
            }
        }
    }

    fun updateUserBalance(dui: String, amountToSubtract: Double, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserByDUI(dui)
                if (user == null) {
                    onResult(false, "Usuario no encontrado")
                    return@launch
                }

                if (user.balance < amountToSubtract) {
                    onResult(false, "Saldo insuficiente")
                    return@launch
                }

                userRepository.updateUserBalance(dui, user.balance - amountToSubtract)
                onResult(true, "Balance actualizado correctamente")
            } catch (e: Exception) {
                onResult(false, "Error actualizando balance: ${e.message}")
            }
        }
    }

    fun loadHotelsAndRooms() {
        viewModelScope.launch {
            try {
                val hotels = hotelRepository.getAllHotels() ?: emptyList()
                val rooms = roomRepository.getAllRoomsAvailable() ?: emptyList()

                // Filtrar duplicados por tipo y precio
                val uniqueRooms = rooms.distinctBy { it.name to it.price }

                uiState = uiState.copy(
                    hotels = hotels,
                    rooms = uniqueRooms
                )
            } catch (e: Exception) {
                uiState = uiState.copy(error = "Error cargando hoteles o habitaciones: ${e.message}")
            }
        }
    }

    /*
    fun loadHotelsAndRooms() {
        viewModelScope.launch {
            try {
                val hotels = hotelRepository.getAllHotels() ?: emptyList()
                val rooms = roomRepository.getAllRoomsAvailable() ?: emptyList()
                uiState = uiState.copy(hotels = hotels, rooms = rooms)
            } catch (e: Exception) {
                uiState = uiState.copy(error = "Error cargando hoteles o habitaciones: ${e.message}")
            }
        }
    }*/

    fun selectHotel(hotel: Hotel) {
        uiState = uiState.copy(selectedHotel = hotel)
        updateTotal()
    }

    fun selectRoom(room: Room) {
        uiState = uiState.copy(selectedRoom = room)
        updateTotal()
    }

    private fun updateTotal() {
        val total = uiState.selectedRoom?.price ?: 0.0
        uiState = uiState.copy(total = total)
    }

    fun cancelSelection() {
        uiState = uiState.copy(selectedHotel = null, selectedRoom = null, total = 0.0, message = null, error = null)
    }

    fun confirmReservation(dui: String, days: Int = 1, onResult: (Boolean, String) -> Unit) {
        val hotel = uiState.selectedHotel
        val room = uiState.selectedRoom

        if (hotel == null || room == null) {
            onResult(false, "Debes seleccionar hotel y habitación")
            return
        }

        viewModelScope.launch {
            try {
                val cost = room.price * days

                // Crear reservación
                val reservation = Reservation(
                    dui = dui,
                    hotelId = hotel.id,
                    roomId = room.id,
                    entryDate = LocalDate.now().toString(),
                    expirationDate = LocalDate.now().plusDays(days.toLong()).toString()
                )
                reservationRepository.createReservation(reservation)
                roomRepository.setRoomNotAvailable(room.id)

                // Crear transacción
                val transaction = Transaction(
                    dui = dui,
                    acquiredService = "Reservación Hotel: ${hotel.name}, Habitación: ${room.name}",
                    transactionDate = LocalDate.now().toString(),
                    amount = cost
                )
                transactionRepository.createTransaction(transaction)

                uiState = uiState.copy(message = "Reservación realizada correctamente")
                cancelSelection()
                onResult(true, "Reservación realizada correctamente")
            } catch (e: Exception) {
                uiState = uiState.copy(error = "Error creando reservación: ${e.message}")
                onResult(false, "Error creando reservación: ${e.message}")
            }
        }
    }
}



/*
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
}*/