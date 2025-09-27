//Dataclass creada para que la pantalla pueda observar estos estados durante la ejecución.
package com.example.paradiseresorts.ui.classes

data class SplashUiState(
    val isLoading: Boolean = true,
    val isSessionActive: Boolean? = null,
    val errorMessage: String? = null
)