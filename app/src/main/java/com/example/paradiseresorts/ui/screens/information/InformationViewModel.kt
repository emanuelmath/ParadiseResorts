//Este archivo contiene la definición del ViewModel respectivo de InformationScreen
package com.example.paradiseresorts.ui.screens.information

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.HotelRepository
import com.example.paradiseresorts.data.repository.RoomRepository
import com.example.paradiseresorts.ui.classes.InformationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InformationViewModel(
    private val hotelRepository: HotelRepository,
    private val roomRepository: RoomRepository
) : ViewModel() {

    companion object {
        private const val TAG = "informationVM"
    }

    var uiState: InformationUiState by mutableStateOf(InformationUiState())
        private set

    fun loadHotelsAndRooms() {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)

                val hotels = hotelRepository.getAllHotels() ?: emptyList()
                val rooms = roomRepository.getAllRoomsAvailable() ?: emptyList()

                uiState = uiState.copy(
                    isLoading = false,
                    hotels = hotels,
                    rooms = rooms
                )

                Log.d(TAG, "Hoteles y habitaciones cargados")
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = e.message)
                Log.e(TAG, "Error cargando info", e)
            }
        }
    }
}

