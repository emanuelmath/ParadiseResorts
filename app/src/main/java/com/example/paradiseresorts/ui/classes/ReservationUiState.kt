package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.Reservation

data class ReservationUiState(
    val isLoading: Boolean = false,
    val reservations: List<Reservation> = emptyList(),
    val error: String? = null,
    val message: String? = null
)