//Este archivo contiene la estructura del viewmodel para la pantalla de arranque o splash
package com.example.paradiseresorts.ui.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.domain.models.Session
import com.example.paradiseresorts.ui.classes.SplashUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(private val sessionRepository: SessionRepository): ViewModel() {

    //Etiqueta para filtrar los logs en LogCat referentes a este VM:
    companion object {
        private const val TAG = "splashVM"
    }

    var uiState: SplashUiState = SplashUiState()
        private set

    fun checkSession(onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d(TAG, "Iniciando verificación de sesión...")
                uiState = uiState.copy(isLoading = true, errorMessage = null)

                delay(1500)

                val currentSessionValue: Session? = sessionRepository.obtainCurrentSession()
                val hasActiveSession: Boolean = currentSessionValue != null
                val hasDUILogged: String? =  currentSessionValue?.dui

                Log.d(TAG, "Resultado de sesión: $hasActiveSession")
                uiState = uiState.copy(isLoading = false, isSessionActive = hasActiveSession, duiSession = hasDUILogged)

                onResult(hasActiveSession)

            } catch (e: Exception) {
                Log.e(TAG, "Error verificando la sesión: ${e.message}", e)
                uiState = uiState.copy(
                    isLoading = false,
                    isSessionActive = false,
                    errorMessage = "Error al validar sesión"
                )
                onResult(false)
            }
        }
    }
}