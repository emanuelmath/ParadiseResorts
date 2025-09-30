package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.Service

data class ServicesUiState(
    val isLoading: Boolean = false,
    val offeredServices: List<Service> = emptyList(),
    val userServices: List<Service> = emptyList(),
    val error: String? = null,
    val message: String? = null
)