package com.example.paradiseresorts.ui.classes

data class LoginUiState(
    val emailOrDUI: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isLoggedIn: Boolean = false
)