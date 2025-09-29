//Este archivo contiene los cambios de estado observables desde la UI de HomeContent
package com.example.paradiseresorts.ui.classes

import com.example.paradiseresorts.domain.models.User

data class HomeContentUiState(
    val dui: String? = null,
    val isLoading: Boolean = false,
    val currentUser: User? = null,
    val welcomeMessage: String = "Bienvenido a pantalla HOME",
    val errorMessage: String? = null
)