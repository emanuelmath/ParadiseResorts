//Este archivo contiene la clase VMFactory para implementar los repositories como parámetros de VM
package com.example.paradiseresorts.ui.screens.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.RoomRepository

class ReservationViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            val reservationRepository = ReservationRepository(ParadiseResortsApplication.database.ReservationDao())
            val roomRepository = RoomRepository(ParadiseResortsApplication.database.RoomDao())
            return ReservationViewModel(reservationRepository, roomRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
