//Este archivo contiene los observables desde la UI de information.

package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.Hotel
import com.example.paradiseresorts.domain.models.Room


data class InformationUiState(
    val isLoading: Boolean = false,
    val hotels: List<Hotel> = emptyList(),
    val rooms: List<Room> = emptyList(),
    val error: String? = null
)