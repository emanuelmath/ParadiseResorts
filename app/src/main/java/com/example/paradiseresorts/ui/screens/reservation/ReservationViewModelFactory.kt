//Este archivo contiene la clase VMFactory para implementar los repositories como parámetros de VM
package com.example.paradiseresorts.ui.screens.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.HotelRepository
import com.example.paradiseresorts.data.repository.ReservationRepository
import com.example.paradiseresorts.data.repository.RoomRepository
import com.example.paradiseresorts.data.repository.TransactionRepository
import com.example.paradiseresorts.data.repository.UserRepository

class ReservationViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            val db = ParadiseResortsApplication.database
            return ReservationViewModel(
                reservationRepository = ReservationRepository(db.ReservationDao()),
                transactionRepository = TransactionRepository(db.TransactionDao()),
                roomRepository = RoomRepository(db.RoomDao()),
                hotelRepository = HotelRepository(db.HotelDao()),
                userRepository = UserRepository(db.UserDao())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
