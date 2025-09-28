//Este archivo contiene los cambios de estado observables desde la UI de HomeContent
package com.example.paradiseresorts.ui.classes

data class HomeContentUiState(
    val isLoading: Boolean = false,
    val welcomeMessage: String = "Bienvenido a pantalla HOME",
    val errorMessage: String? = null
)