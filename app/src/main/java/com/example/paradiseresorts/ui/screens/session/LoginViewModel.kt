//Este archivo contiene el ViewModel que aplica la lógica de la pantalla LoginScreen
package com.example.paradiseresorts.ui.screens.session

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.paradiseresorts.ui.classes.LoginUiState
import kotlinx.coroutines.delay

class LoginViewModel : ViewModel() {

    companion object {
        private const val TAG = "LoginVM"

        // Usuario de prueba
        private const val TEST_USERNAME = "admin"
        private const val TEST_EMAIL = "admin@gmail.com"
        private const val TEST_PASSWORD = "12345678"
    }

    var uiState by mutableStateOf(LoginUiState())
        private set

    fun startSession() {
        viewModelScope.launch {
            // Validaciones iniciales
            if (uiState.usernameOrEmail.isBlank() || uiState.password.isBlank()) {
                uiState = uiState.copy(errorMessage = "Todos los campos son obligatorios")
                return@launch
            }

            // Validación de longitud de contraseña
            if (uiState.password.length < 8) {
                uiState = uiState.copy(errorMessage = "La contraseña debe tener al menos 8 caracteres")
                return@launch
            }

            try {
                uiState = uiState.copy(isLoading = true, errorMessage = null)
                Log.d(TAG, "Intentando login con: ${uiState.usernameOrEmail}")
                delay(1000)

                // Validación contra usuario de prueba
                val matchesAdmin = (
                        (uiState.usernameOrEmail == TEST_USERNAME || uiState.usernameOrEmail == TEST_EMAIL) &&
                                uiState.password == TEST_PASSWORD
                        )

                if (matchesAdmin) {
                    uiState = uiState.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        errorMessage = null
                    )
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = "Usuario o contraseña incorrectos"
                    )
                }

            } catch (e: Exception) {
                val msg = "Error al iniciar sesión"
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = msg
                )
                Log.e(TAG, "Error: ${e.message}", e)
            }
        }
    }

    fun onUsernameOrEmailChange(newValue: String) {
        uiState = uiState.copy(usernameOrEmail = newValue, errorMessage = null)
    }

    fun onPasswordChange(newValue: String) {
        uiState = uiState.copy(password = newValue, errorMessage = null)
    }

    fun clearError() {
        uiState = uiState.copy(errorMessage = null)
    }
}