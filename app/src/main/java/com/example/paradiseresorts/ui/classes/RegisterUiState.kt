package com.example.paradiseresorts.ui.classes

data class RegisterUiState(
    val dui: String = "",
    val name: String = "",
    val lastName: String = "",
    val dateOfBirth: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val phoneNumber: String = "",
    val cardInfo: CardInfo? = null,
    val acceptTerms: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRegistered: Boolean = false
)