package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.Card
import com.example.paradiseresorts.domain.models.User


data class ProfileUiState(
    val dui: String, // Obligatorio
    var nameToEdit: String? = null,
    var lastNameToEdit: String? = null,
    var phoneNumberToEdit: String? = null,
    val userInProfile: User? = null, // Para mostrar otros campos visuales
    val cards: List<Card> = emptyList(), // 🔹 Lista de tarjetas del usuario
    val isLoggingOut: Boolean = false,
    val errorMessage: String? = null
)

/*
data class ProfileUiState(
    val dui: String, // -> Obligatorio, ya que la navegación entre estas pantallas siempre dependerán del usuario que está activo.
    var nameToEdit: String? = null,
    var lastNameToEdit: String? = null,
    var phoneNumberToEdit: String? = null,
    val userInProfile: User? = null, // -> Para recuperar otros campos visuales, como el balance y así.
    val isLoggingOut: Boolean = false,
    val errorMessage: String? = null
)*/