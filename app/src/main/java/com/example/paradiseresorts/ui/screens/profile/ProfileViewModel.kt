//Este archivo contiene la definición del ViewModel para la pantalla de perfil:
package com.example.paradiseresorts.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.ui.classes.ProfileUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    companion object {
        private const val TAG = "profileVM"
    }

    var uiState by mutableStateOf(ProfileUiState("123456789"))
        //Le paso un DUI falso para evitar el error, aquí hay que ver cómo pasarlo y si se le pasa al screen o al vm factory.
        private set

    fun logout(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoggingOut = true, errorMessage = null)

                // Simular un proceso
                delay(1000)
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
}