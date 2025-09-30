package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.Hotel
import com.example.paradiseresorts.domain.models.Room

data class ReservationFormUiState(
    val hotels: List<Hotel> = emptyList(),
    val rooms: List<Room> = emptyList(),
    val selectedHotel: Hotel? = null,
    val selectedRoom: Room? = null,
    val total: Double = 0.0,
    val message: String? = null,
    val error: String? = null
)
