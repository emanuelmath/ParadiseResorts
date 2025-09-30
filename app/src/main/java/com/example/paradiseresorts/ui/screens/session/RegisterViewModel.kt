//Este archivo contiene la clase ViewModel para lógica de la pantalla de registro:
package com.example.paradiseresorts.ui.screens.session

import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.CardRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.domain.models.Card
import com.example.paradiseresorts.domain.models.User
import com.example.paradiseresorts.ui.classes.CardInfo
import com.example.paradiseresorts.ui.classes.RegisterUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import java.time.LocalDate
import java.time.Period


class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    companion object {
        private const val TAG = "RegisterVM"
    }

    var uiState by mutableStateOf(RegisterUiState())
        private set

    // Cambios en inputs
    fun onDuiChange(value: String) { uiState = uiState.copy(dui = value, errorMessage = null) }
    fun onNameChange(value: String) { uiState = uiState.copy(name = value, errorMessage = null) }
    fun onLastNameChange(value: String) { uiState = uiState.copy(lastName = value, errorMessage = null) }
    fun onDateOfBirthChange(value: String) { uiState = uiState.copy(dateOfBirth = value, errorMessage = null) }
    fun onEmailChange(value: String) { uiState = uiState.copy(email = value, errorMessage = null) }
    fun onPasswordChange(value: String) { uiState = uiState.copy(password = value, errorMessage = null) }
    fun onConfirmPasswordChange(value: String) { uiState = uiState.copy(confirmPassword = value, errorMessage = null) }
    fun onPhoneNumberChange(value: String) { uiState = uiState.copy(phoneNumber = value, errorMessage = null) }
    fun onAcceptTermsChange(value: Boolean) { uiState = uiState.copy(acceptTerms = value, errorMessage = null) }

    fun clearError() { uiState = uiState.copy(errorMessage = null) }

    fun validateStep(step: Int, onValid: () -> Unit) {
        viewModelScope.launch {
            when (step) {
                0 -> {
                    if (!uiState.dui.matches(Regex("\\d{9}"))) {
                        uiState = uiState.copy(errorMessage = "DUI inválido")
                        return@launch
                    }
                    delay(500)
                    if (userRepository.getUserByDUI(uiState.dui) != null) {
                        uiState = uiState.copy(errorMessage = "DUI ya registrado")
                    } else onValid()
                }
                1 -> if (uiState.name.isNotBlank()) onValid() else uiState = uiState.copy(errorMessage = "Nombre requerido")
                2 -> if (uiState.lastName.isNotBlank()) onValid() else uiState = uiState.copy(errorMessage = "Apellido requerido")
                3 -> {
                    try {
                        val dob = LocalDate.parse(uiState.dateOfBirth)
                        val age = Period.between(dob, LocalDate.now()).years
                        if (age < 18) {
                            uiState = uiState.copy(errorMessage = "Debes ser mayor de 18 años")
                            return@launch
                        }
                        onValid()
                    } catch (e: Exception) {
                        uiState = uiState.copy(errorMessage = "Fecha inválida")
                    }
                }
                4 -> {
                    if (!Patterns.EMAIL_ADDRESS.matcher(uiState.email).matches()) {
                        uiState = uiState.copy(errorMessage = "Email inválido")
                        return@launch
                    }
                    delay(500)
                    if (userRepository.getUserByEmail(uiState.email) != null) {
                        uiState = uiState.copy(errorMessage = "Email ya registrado")
                    } else onValid()
                }
                5 -> {
                    if (uiState.password.length < 8) {
                        uiState = uiState.copy(errorMessage = "Contraseña muy corta")
                        return@launch
                    }
                    if (uiState.password != uiState.confirmPassword) {
                        uiState = uiState.copy(errorMessage = "Contraseñas no coinciden")
                        return@launch
                    }
                    onValid()
                }
                6 -> {
                    if (!uiState.phoneNumber.matches(Regex("\\d{8}"))) {
                        uiState = uiState.copy(errorMessage = "Teléfono inválido")
                        return@launch
                    }
                    delay(500)
                    if (userRepository.getUserByPhoneNumber(uiState.phoneNumber) != null) {
                        uiState = uiState.copy(errorMessage = "Teléfono ya registrado")
                    } else onValid()
                }
                7 -> {
                    if (!uiState.acceptTerms) {
                        uiState = uiState.copy(errorMessage = "Debes aceptar los términos")
                    } else {
                        // Crear usuario
                        val user = User(
                            dui = uiState.dui,
                            name = uiState.name,
                            lastName = uiState.lastName,
                            email = uiState.email,
                            password = BCrypt.hashpw(uiState.password, BCrypt.gensalt()),
                            dateOfBirthday = uiState.dateOfBirth,
                            phoneNumber = uiState.phoneNumber,
                            balance = 0.0
                        )
                        userRepository.createUser(user)
                        onValid()
                    }
                }
            }
        }
    }

    fun registerUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000)
            uiState = uiState.copy(isLoading = false, isRegistered = true)
            onSuccess()
        }
    }
}


/*
class RegisterViewModel(private val userRepository: UserRepository,
    private val cardRepository: CardRepository): ViewModel() {

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
                    val user = userRepository.getUserByDUI(uiState.dui)
                    if (user != null) {
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
                    if (userRepository.getUserByEmail(uiState.email) != null) {
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
                    if (userRepository.getUserByPhoneNumber(uiState.phoneNumber) != null) {
                        uiState = uiState.copy(isLoading = false, errorMessage = "Teléfono ya registrado")
                    } else {
                        uiState = uiState.copy(isLoading = false, errorMessage = null)
                        onValid()
                    }
                }
                7 -> {
                    //                    if(cardRepository.getCardByCode()) {
                    //
                    //                    }
                    onValid()
                }
                8 -> {
                    if (!uiState.acceptTerms) {
                        uiState = uiState.copy(errorMessage = "Debes aceptar los términos")
                    } else {
                        onValid()
                        val userToCreate = User(
                            dui = uiState.dui,
                            name = uiState.name,
                            lastName = uiState.lastName,
                            email = uiState.email,
                            password = BCrypt.hashpw(uiState.password, BCrypt.gensalt()),
                            dateOfBirthday = uiState.dateOfBirth,
                            phoneNumber = uiState.phoneNumber,
                            balance = 0.0
                        )
                        userRepository.createUser(userToCreate)
                        if(uiState.cardInfo != null) {
                            val cardToCreate = Card(
                                code = uiState.cardInfo!!.code,
                                expirationDate = uiState.cardInfo!!.expirationDate,
                                cvv = uiState.cardInfo!!.cvv,
                                dui = uiState.cardInfo!!.dui
                            )
                            cardRepository.createCard(cardToCreate)
                        }
                    }
                }
            }
        }
    }

    //Función de añadir tarjeta
    fun onCardAdded(code: String, expirationDate: String, cvv: String) {
            uiState = uiState.copy(
                cardInfo = CardInfo(
                    code = code,
                    expirationDate = expirationDate,
                    dui = uiState.dui,
                    cvv = cvv
                ),
                errorMessage = null
            )

        //Poner validaciones para el tiempo, si el uiState no se actualizó, se tarda mucho, etc...
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
}*/