//Archivo con la definición del VMFactory para inyectar repositorios como parámetros en el VM
package com.example.paradiseresorts.ui.screens.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.HotelRepository
import com.example.paradiseresorts.data.repository.RoomRepository

class InformationViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationViewModel::class.java)) {
            val hotelRepository = HotelRepository(ParadiseResortsApplication.database.HotelDao())
            val roomRepository = RoomRepository(ParadiseResortsApplication.database.RoomDao())
            return InformationViewModel(hotelRepository, roomRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}