//Este archivo contiene la clase ViewModel para lógica de la pantalla de registro:
package com.example.paradiseresorts.ui.screens.session

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.ui.classes.CardInfo
import com.example.paradiseresorts.ui.classes.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period

class RegisterViewModel(/*Introducir repositorios a utilizar*/): ViewModel() {

    companion object {
        private const val TAG = "RegisterVM"
    }

    var uiState by mutableStateOf(RegisterUiState())
        private set

    // Funciones de cambios de estado en los inputs:
    fun onDuiChange(value: String) { uiState = uiState.copy(dui = value, errorMessage = null) }
    fun onNameChange(value: String) { uiState = uiState.copy(name = value, errorMessage = null) }
    fun onLastNameChange(value: String) { uiState = uiState.copy(lastName = value, errorMessage = null) }
    fun onDateOfBirthChange(value: String) { uiState = uiState.copy(dateOfBirth = value, errorMessage = null) }
    fun onEmailChange(value: String) { uiState = uiState.copy(email = value, errorMessage = null) }
    fun onPasswordChange(value: String) { uiState = uiState.copy(password = value, errorMessage = null) }
    fun onConfirmPasswordChange(value: String) { uiState = uiState.copy(confirmPassword = value, errorMessage = null) }
    fun onPhoneNumberChange(value: String) { uiState = uiState.copy(phoneNumber = value, errorMessage = null) }
    fun onAcceptTermsChange(value: Boolean) { uiState = uiState.copy(acceptTerms = value, errorMessage = null) }

    //Limpiar mensaje de error
    fun clearError() { uiState = uiState.copy(errorMessage = null) }

    // Función de validaciones por cada paso del formulario:
    fun validateStep(step: Int, onValid: () -> Unit) {
        viewModelScope.launch {
            when (step) {
                0 -> {
                    if (!uiState.dui.matches(Regex("\\d{9}"))) {
                        uiState = uiState.copy(errorMessage = "DUI inválido. Debe tener 9 dígitos sin guión.")
                        return@launch
                    }
                    // (Simulación) Esperar tiempo para validar si el DUI ya está registrado
                    uiState = uiState.copy(isLoading = true)
                    delay(1000)
                    if (uiState.dui == "123456789") {
                        uiState = uiState.copy(isLoading = false, errorMessage = "DUI ya registrado")
                    } else {
                        uiState = uiState.copy(isLoading = false, errorMessage = null)
                        onValid()
                    }
                }
                1 -> { if (uiState.name.isNotBlank()) onValid() else uiState = uiState.copy(errorMessage = "Nombre requerido") }
                2 -> { if (uiState.lastName.isNotBlank()) onValid() else uiState = uiState.copy(errorMessage = "Apellido requerido") }
                3 -> {
                    try {
                        val dob = LocalDate.parse(uiState.dateOfBirth) // yyyy-MM-dd
                        val today = LocalDate.now()
                        if (dob.isAfter(today)) {
                            uiState = uiState.copy(errorMessage = "La fecha no puede ser futura")
                            return@launch
                        }
                        val age = Period.between(dob, today).years
                        if (age < 18) {
                            uiState = uiState.copy(errorMessage = "Debes tener al menos 18 años")
                            return@launch
                        }
                        onValid()
                    } catch (e: Exception) {
                        uiState = uiState.copy(errorMessage = "Formato inválido de fecha (yyyy-MM-dd)")
                    }
                }
                4 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()) {
                        uiState = uiState.copy(errorMessage = "Formato de email inválido")
                        return@launch
                    }
                    uiState = uiState.copy(isLoading = true)
                    delay(1000)
                    if (uiState.email == "test@email.com") {
                        uiState = uiState.copy(isLoading = false, errorMessage = "Email ya registrado")
                    } else {
                        uiState = uiState.copy(isLoading = false, errorMessage = null)
                        onValid()
                    }
                }
                5 -> {
                    if (uiState.password.length < 8) {
                        uiState = uiState.copy(errorMessage = "La contraseña debe tener al menos 8 caracteres")
                        return@launch
                    }
                    if (uiState.password != uiState.confirmPassword) {
                        uiState = uiState.copy(errorMessage = "Las contraseñas no coinciden")
                        return@launch
                    }
                    onValid()
                }
                6 -> {
                    if (!uiState.phoneNumber.matches(Regex("\\d{8}"))) {
                        uiState = uiState.copy(errorMessage = "Teléfono inválido (8 dígitos)")
                        return@launch
                    }
                    uiState = uiState.copy(isLoading = true)
                    delay(1000)
                    if (uiState.phoneNumber == "12345678") {
                        uiState = uiState.copy(isLoading = false, errorMessage = "Teléfono ya registrado")
                    } else {
                        uiState = uiState.copy(isLoading = false, errorMessage = null)
                        onValid()
                    }
                }
                7 -> {
                    onValid()
                }
                8 -> {
                    if (!uiState.acceptTerms) {
                        uiState = uiState.copy(errorMessage = "Debes aceptar los términos")
                    } else onValid()
                }
            }
        }
    }

    //Función de añadir tarjeta
    fun onCardAdded(number: String, holder: String, expiry: String, cvv: String) {

        /* Añadir lógica de ingreso de tarjeta */

        uiState = uiState.copy(
            cardInfo = CardInfo(
                number = number,
                holder = holder,
                expiry = expiry,
                cvv = cvv
            ),
            errorMessage = null
        )
    }

    // Función de registro final:
    fun registerUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(timeMillis = 1000)
            uiState = uiState.copy(isLoading = false, isRegistered = true, errorMessage = null)
            Log.d(TAG, "Registro exitoso para: ${uiState.email}")
            onSuccess()
        }
    }
}