package com.example.paradiseresorts.ui.classes

data class LoginUiState(
    val emailOrDUI: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val dui: String? = null,
    val errorMessage: String? = null,
    val isClearingFields: Boolean = false
)