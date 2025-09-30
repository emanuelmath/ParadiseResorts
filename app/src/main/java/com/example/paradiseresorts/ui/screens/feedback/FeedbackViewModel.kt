//Este archivo contiene la definición del ViewModel respectivo de FeedbackScreen
package com.example.paradiseresorts.ui.screens.feedback

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.FeedbackRepository
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Feedback
import com.example.paradiseresorts.ui.classes.FeedbackUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.example.paradiseresorts.domain.models.Reservation

class FeedbackViewModel(
    private val feedbackRepository: FeedbackRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {

    companion object {
        private const val TAG = "FeedbackVM"
    }

    var uiState by mutableStateOf(FeedbackUiState())
        private set

    init {
        loadAllFeedbacks()
    }

    // Obtener fecha de hoy
    private fun getTodayDate(): String =
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    // Obtener DUI del usuario actualmente logeado
    private suspend fun getCurrentUserDUI(): String? {
        val session = sessionRepository.obtainCurrentSession()
        return session?.dui
    }

    // Verificar si puede dar feedback (tiene reserva hoy)
    private suspend fun hasActiveReservation(dui: String): Boolean {
        return try {
            val reservations = reservationRepository.getAllReservationByUserDUI(dui, getTodayDate())
            !reservations.isNullOrEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "Error verificando reservaciones", e)
            false
        }
    }

    fun addFeedbackForCurrentUser(opinion: String, rate: Int) {
        viewModelScope.launch {
            val session = sessionRepository.obtainCurrentSession()
            val dui = session?.dui ?: run {
                uiState = uiState.copy(feedbackMessage = "No se pudo obtener usuario logeado")
                return@launch
            }

            // Obtener datos completos del usuario desde UserRepository
            val user = try {
                userRepository.getUserByDUI(dui)
            } catch (e: Exception) {
                Log.e(TAG, "Error obteniendo usuario", e)
                null
            }

            if (user == null) {
                uiState = uiState.copy(feedbackMessage = "No se pudo obtener usuario")
                return@launch
            }

            val userName = user.name
            val userLastName = user.lastName

            // Verificar que tenga una reserva activa hoy
            val today = getTodayDate()
            val reservations = try {
                reservationRepository.getAllReservationByUserDUI(dui, today)
            } catch (e: Exception) {
                Log.e(TAG, "Error obteniendo reservas", e)
                emptyList<Reservation>()
            }

            val activeReservation = reservations?.firstOrNull()
            if (activeReservation == null) {
                uiState = uiState.copy(feedbackMessage = "No puedes opinar sin una reserva activa")
                return@launch
            }

            // Crear feedback completo
            val feedback = Feedback(
                name = userName,
                lastName = userLastName,
                opinion = opinion,
                rate = rate,
                dui = dui,
                roomId = activeReservation.roomId,
                hotelId = activeReservation.hotelId
            )

            try {
                feedbackRepository.addFeedback(feedback.toEntity())
                loadAllFeedbacks()
                uiState = uiState.copy(feedbackMessage = "Feedback agregado correctamente")
            } catch (e: Exception) {
                uiState = uiState.copy(feedbackMessage = "Error al agregar feedback")
                Log.e(TAG, "Error agregando feedback", e)
            }
        }
    }

    // Cargar todos los feedbacks
    fun loadAllFeedbacks() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val feedbackList = feedbackRepository.getAllFeedbacks().map { it.toModel() }
                uiState = uiState.copy(
                    isLoading = false,
                    feedbacks = feedbackList
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, feedbackMessage = "Error al cargar feedbacks")
                Log.e(TAG, "Error cargando feedbacks", e)
            }
        }
    }
}

/*
class FeedbackViewModel(
    private val feedbackRepository: FeedbackRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    companion object {
        private const val TAG = "FeedbackVM"
    }

    var uiState by mutableStateOf(FeedbackUiState())
        private set

    init {
        loadAllFeedbacks()
    }

    // Obtener fecha de hoy
    private fun getTodayDate(): String =
        LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    // Obtener DUI del usuario actual
    private suspend fun getCurrentUserDUI(): String? {
        val user = userRepository.getUserByEmail("email@example.com")
        return user?.dui
    }

    // Verificar si puede dar feedback (tiene reserva hoy)
    private suspend fun hasActiveReservation(dui: String): Boolean {
        return try {
            val reservations = reservationRepository.getAllReservationByUserDUI(dui, getTodayDate())
            !reservations.isNullOrEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "Error verificando reservaciones", e)
            false
        }
    }

    // Agregar feedback del usuario actual
    fun addFeedbackForCurrentUser(opinion: String, rate: Int) {
        viewModelScope.launch {
            val dui = getCurrentUserDUI() ?: run {
                uiState = uiState.copy(feedbackMessage = "No se pudo obtener usuario")
                return@launch
            }

            if (!hasActiveReservation(dui)) {
                uiState = uiState.copy(feedbackMessage = "No puedes opinar sin una reserva activa")
                return@launch
            }

            val feedback = Feedback(
                name = "",
                lastName = "",
                opinion = opinion,
                rate = rate,
                dui = dui,
                roomId = 0,
                hotelId = 0
            )

            try {
                feedbackRepository.addFeedback(feedback.toEntity())
                loadAllFeedbacks()
                uiState = uiState.copy(feedbackMessage = "Feedback agregado correctamente")
            } catch (e: Exception) {
                uiState = uiState.copy(feedbackMessage = "Error al agregar feedback")
                Log.e(TAG, "Error agregando feedback", e)
            }
        }
    }

    // Cargar todos los feedbacks
    fun loadAllFeedbacks() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val feedbackList = feedbackRepository.getAllFeedbacks().map { it.toModel() }
                uiState = uiState.copy(
                    isLoading = false,
                    feedbacks = feedbackList
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, feedbackMessage = "Error al cargar feedbacks")
                Log.e(TAG, "Error cargando feedbacks", e)
            }
        }
    }
}
*/
/*
class FeedbackViewModel(
    private val feedbackRepository: FeedbackRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    companion object {
        private const val TAG = "feedbackVM"
    }

    var uiState by mutableStateOf(FeedbackUiState())
        private set

    // Obtener fecha de hoy
    fun getTodayDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return LocalDate.now().format(formatter)
    }

    // Obtener DUI del usuario actual (puede adaptarse a tu sesión real)
    suspend fun getCurrentUserDUI(): String? {
        // Ejemplo: tomar el primer usuario de la DB o usar sesión guardada
        val user = userRepository.getUserByEmail("email@example.com")
        return user?.dui
    }

    // Validar si el usuario puede dar feedback
    private suspend fun canUserGiveFeedback(dui: String, todayDate: String): Boolean {
        return try {
            val reservations = reservationRepository.getAllReservationByUserDUI(dui, todayDate)
            !reservations.isNullOrEmpty()
        } catch (e: Exception) {
            Log.e(TAG, "Error verificando reservaciones", e)
            false
        }
    }

    // Agregar feedback solo si tiene reservaciones
    fun addFeedbackForCurrentUser(feedback: Feedback) {
        viewModelScope.launch {
            val dui = feedback.dui.ifBlank { getCurrentUserDUI() ?: return@launch }
            val todayDate = getTodayDate()
            if (canUserGiveFeedback(dui, todayDate)) {
                addFeedback(feedback.copy(dui = dui))
            } else {
                uiState = uiState.copy(feedbackMessage = "No puedes opinar sin tener reservaciones")
            }
        }
    }

    // CRUD feedbacks
    fun loadAllFeedbacks() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            try {
                val feedbackList = feedbackRepository.getAllFeedbacks().map { it.toModel() }
                uiState = uiState.copy(
                    isLoading = false,
                    feedbacks = feedbackList,
                    feedbackMessage = "Feedback cargado correctamente"
                )
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, feedbackMessage = "Error al cargar feedbacks")
                Log.e(TAG, "Error cargando feedbacks", e)
            }
        }
    }

    private fun addFeedback(feedback: Feedback) {
        viewModelScope.launch {
            try {
                feedbackRepository.addFeedback(feedback.toEntity())
                loadAllFeedbacks()
                uiState = uiState.copy(feedbackMessage = "Feedback agregado correctamente")
            } catch (e: Exception) {
                uiState = uiState.copy(feedbackMessage = "Error al agregar feedback")
                Log.e(TAG, "Error agregando feedback", e)
            }
        }
    }
}
*/