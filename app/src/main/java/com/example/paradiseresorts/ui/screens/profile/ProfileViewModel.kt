//Este archivo contiene la definición del ViewModel para la pantalla de perfil:
package com.example.paradiseresorts.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.CardRepository
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.domain.models.Card
import com.example.paradiseresorts.domain.models.Session
import com.example.paradiseresorts.ui.classes.ProfileUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    private val cardRepository: CardRepository // 🔹 Inyectar el repo de tarjetas
) : ViewModel() {

    companion object {
        private const val TAG = "profileVM"
    }

    var uiState by mutableStateOf(ProfileUiState(dui = "", cards = emptyList()))
        private set

    fun loadUser(dui: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserByDUI(dui)
                val userCards = cardRepository.getCardByDUI(dui)?.let { listOf(it) } ?: emptyList()
                uiState = uiState.copy(
                    dui = dui,
                    userInProfile = user,
                    cards = userCards,
                    errorMessage = null
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error cargando usuario: ${e.message}", e)
                uiState = uiState.copy(errorMessage = "Error cargando usuario")
            }
        }
    }

    fun getCurrentSession(): Session? {
        var session: Session? = null
        runBlocking {
            session = sessionRepository.obtainCurrentSession()
        }
        return session
    }

    fun logout(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoggingOut = true, errorMessage = null)

                delay(1000) // simula proceso
                sessionRepository.deleteAllSessions()

                uiState = uiState.copy(isLoggingOut = false)
                onResult(true)
            } catch (e: Exception) {
                Log.e(TAG, "Error cerrando sesión: ${e.message}", e)
                uiState = uiState.copy(
                    isLoggingOut = false,
                    errorMessage = "No se pudo cerrar la sesión"
                )
                onResult(false)
            }
        }
    }

    // 🔹 Función para añadir tarjeta
    fun addCard(code: String, expirationDate: String, cvv: String) {
        val dui = uiState.dui
        if (dui.isBlank()) {
            Log.e(TAG, "No hay DUI válido para añadir tarjeta")
            return
        }

        viewModelScope.launch {
            try {
                // Crear entidad de tarjeta
                val newCard = Card(
                    code = code,
                    expirationDate = expirationDate,
                    cvv = cvv,
                    dui = dui,
                    balance = 2000.0
                )
                cardRepository.createCard(newCard)

                // Actualizar lista de tarjetas en uiState
                val updatedCards = uiState.cards.toMutableList().apply { add(newCard) }
                uiState = uiState.copy(cards = updatedCards)

                Log.d(TAG, "Tarjeta añadida correctamente: $code")
            } catch (e: Exception) {
                Log.e(TAG, "Error al añadir tarjeta: ${e.message}", e)
                uiState = uiState.copy(errorMessage = "No se pudo añadir la tarjeta")
            }
        }
    }
}


/*
class ProfileViewModel(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    companion object {
        private const val TAG = "profileVM"
    }

    var uiState by mutableStateOf(ProfileUiState(dui = ""))
        private set

    fun loadUser(dui: String) {
        viewModelScope.launch {
            try {
                val user = userRepository.getUserByDUI(dui)
                uiState = uiState.copy(
                    dui = dui,
                    userInProfile = user,
                    errorMessage = null
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error cargando usuario: ${e.message}", e)
                uiState = uiState.copy(errorMessage = "Error cargando usuario")
            }
        }
    }

    fun getCurrentSession(): Session? {
        var session: Session? = null
        runBlocking {
            session = sessionRepository.obtainCurrentSession()
        }
        return session
    }

    fun logout(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoggingOut = true, errorMessage = null)

                delay(1000) // simula proceso
                sessionRepository.deleteAllSessions()

                uiState = uiState.copy(isLoggingOut = false)
                onResult(true)
            } catch (e: Exception) {
                Log.e(TAG, "Error cerrando sesión: ${e.message}", e)
                uiState = uiState.copy(
                    isLoggingOut = false,
                    errorMessage = "No se pudo cerrar la sesión"
                )
                onResult(false)
            }
        }
    }
}*/