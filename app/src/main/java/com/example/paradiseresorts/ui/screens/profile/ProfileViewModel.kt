//Este archivo contiene la definición del ViewModel para la pantalla de perfil:
package com.example.paradiseresorts.ui.screens.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.ui.classes.ProfileUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ProfileViewModel(
    sessionRepository: SessionRepository  //Repository de ejemplo, probablemente necesites otro o no
) : ViewModel() {

    companion object {
        private const val TAG = "profileVM"
    }

    var uiState by mutableStateOf(ProfileUiState())
        private set

    fun logout(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoggingOut = true, errorMessage = null)

                // Simular un proceso
                delay(1000)
                //sessionRepository.clearSession()

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