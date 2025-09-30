//Este archivo contiene la definición del ViewModel perteneciente a la pantalla HomeContent
package com.example.paradiseresorts.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.CardRepository
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.ServiceRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.domain.models.Card
import com.example.paradiseresorts.ui.classes.HomeContentUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class HomeContentViewModel(
    private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository,
    private val serviceRepository: ServiceRepository,
    private val cardRepository: CardRepository
) : ViewModel() {

    var uiState: HomeContentUiState by mutableStateOf(HomeContentUiState())
        private set

    fun obtainCurrenUser(dui: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByDUI(dui)
            val reservations = reservationRepository.getAllReservationByUserDUI(dui, getTodayDateString())
            val services = serviceRepository.getAllServicesByDUI(dui)

            uiState = uiState.copy(
                currentUser = user,
                reservationsOfUser = reservations,
                servicesOfUser = services
            )
        }
    }

    fun obtainCurrentReservationsOfUser(dui: String) {
        viewModelScope.launch {
            val reservations = reservationRepository.getAllReservationByUserDUI(dui, getTodayDateString())
            uiState =  uiState.copy(
                reservationsOfUser = reservations
            )
        }
    }

    fun obtainServices(dui: String) {
        viewModelScope.launch {
            val services = serviceRepository.getAllServicesByDUI(dui)
            uiState = uiState.copy(
                servicesOfUser = services
            )
        }
    }

    suspend fun getCardForUser(dui: String): Card? {
        return cardRepository.getCardByDUI(dui)
    }

    fun rechargeBalance(dui: String, amount: Double, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            val user = userRepository.getUserByDUI(dui)
            val card = cardRepository.getCardByDUI(dui)

            if (user == null || card == null) {
                onResult(false, "No hay tarjeta asociada")
                return@launch
            }

            if (amount <= 0) {
                onResult(false, "El monto debe ser mayor a 0")
                return@launch
            }

            if (amount > card.balance) {
                onResult(false, "Saldo insuficiente en la tarjeta")
                return@launch
            }

            // Actualizar balances
            val newCardBalance = card.balance - amount
            val newUserBalance = user.balance + amount

            cardRepository.updateCardBalance(dui, newCardBalance)
            userRepository.updateUserBalance(dui, newUserBalance)

            // Refrescar datos completos
            val updatedUser = userRepository.getUserByDUI(dui)

            uiState = uiState.copy(currentUser = updatedUser)

            onResult(true, "Se transfirieron $$amount a tu balance")
        }
    }

    fun getTodayDateString(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(Date())
    }
}

/*
class HomeContentViewModel(private val userRepository: UserRepository,
    private val reservationRepository: ReservationRepository, private val serviceRepository: ServiceRepository) : ViewModel() {

    //Etiqueta de filtrado de logs pertenecientes a este VM:
    companion object {
        private const val TAG = "homeContentVM"
    }

    var uiState: HomeContentUiState by mutableStateOf(HomeContentUiState())
        private set

    fun obtainCurrenUser(dui: String) {
        viewModelScope.launch {
            val user = userRepository.getUserByDUI(dui)
            uiState = uiState.copy(currentUser = user)
        }
    }

    fun obtainCurrentReservationsOfUser(dui: String) {
        viewModelScope.launch {
            val reservations = reservationRepository.getAllReservationByUserDUI(dui, getTodayDateString())
            uiState =  uiState.copy(
                reservationsOfUser = reservations
            )
        }
    }

    fun obtainServices(dui: String) {
        viewModelScope.launch {
            val services = serviceRepository.getAllServicesByDUI(dui)
            uiState = uiState.copy(
                servicesOfUser = services
            )
        }
    }

    //Función de ejemplo para refrescar el contenido de Home
    fun refreshHome() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000) // Simulación de carga
            uiState = uiState.copy(
                isLoading = false,
                welcomeMessage = "Contenido actualizado en Home"
            )
            Log.d(TAG, "HomeContent actualizado")
        }
    }

    fun getTodayDateString(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(Date())
    }
}*/