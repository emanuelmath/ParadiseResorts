//Archivo con la definición del VMFactory para inyectar repositorios como parámetros en el VM
package com.example.paradiseresorts.ui.screens.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.FeedbackRepository
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository

class FeedbackViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {

            val feedbackRepository = FeedbackRepository(
                ParadiseResortsApplication.database.FeedbackDao()
            )
            val reservationRepository = ReservationRepository(
                ParadiseResortsApplication.database.ReservationDao()
            )
            val userRepository = UserRepository(
                ParadiseResortsApplication.database.UserDao()
            )
            val sessionRepository = SessionRepository(
                ParadiseResortsApplication.database.SessionDao()
            )

            return FeedbackViewModel(
                feedbackRepository = feedbackRepository,
                reservationRepository = reservationRepository,
                userRepository = userRepository,
                sessionRepository = sessionRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}