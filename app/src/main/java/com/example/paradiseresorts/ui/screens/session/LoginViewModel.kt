//Este archivo contiene el ViewModel que aplica la lógica de la pantalla LoginScreen
package com.example.paradiseresorts.ui.screens.session

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.domain.models.Session
import kotlinx.coroutines.launch
import com.example.paradiseresorts.ui.classes.LoginUiState
import kotlinx.coroutines.delay
import org.mindrot.jbcrypt.BCrypt
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class LoginViewModel(private val userRepository: UserRepository,
                     private val sessionRepository: SessionRepository) : ViewModel() {

    companion object {
        private const val TAG = "LoginVM"

        /*// Usuario de prueba
        private const val TEST_USERNAME = "admin"
        private const val TEST_EMAIL = "admin@gmail.com"
        private const val TEST_PASSWORD = "12345678"*/
    }
    var uiState by mutableStateOf(LoginUiState())
        private set

    fun startSession() {
        viewModelScope.launch {
            // Validaciones iniciales
            if (uiState.emailOrDUI.isBlank() || uiState.password.isBlank()) {
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
                Log.d(TAG, "Intentando login con: ${uiState.emailOrDUI}")
                delay(1000)

                // Validación contra usuario de prueba
                //val matchesAdmin = (
                //        (uiState.usernameOrEmail == TEST_USERNAME || uiState.usernameOrEmail == TEST_EMAIL) &&
                //                uiState.password == TEST_PASSWORD
                //        )

                val user = if (Patterns.EMAIL_ADDRESS.matcher(uiState.emailOrDUI).matches()) {
                    userRepository.getUserByEmail(uiState.emailOrDUI)
                } else {
                    userRepository.getUserByDUI(uiState.emailOrDUI)
                }

                if (user != null) {
                    if(BCrypt.checkpw(uiState.password, user.password)) {
                        uiState = uiState.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            errorMessage = null
                        )
                        val sessionToCreate = Session(
                            dui = user.dui,
                            sessionDate = getTodayDateString()
                        )
                        sessionRepository.createSession(sessionToCreate)
                    } else {
                        uiState = uiState.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            errorMessage = "Contraseña incorrecta."
                        )
                    }
                } else {
                    uiState = uiState.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = "Usuario no existente."
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

    fun onEmailOrDUIChange(newValue: String) {
        uiState = uiState.copy(emailOrDUI = newValue, errorMessage = null)
    }

    fun onPasswordChange(newValue: String) {
        uiState = uiState.copy(password = newValue, errorMessage = null)
    }

    fun clearError() {
        uiState = uiState.copy(errorMessage = null)
    }

    fun getTodayDateString(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(Date())
    }
}