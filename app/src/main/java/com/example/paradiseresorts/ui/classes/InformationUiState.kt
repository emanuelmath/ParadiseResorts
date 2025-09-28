//Este archivo contiene los observables desde la UI de information.

package com.example.paradiseresorts.ui.classes

data class InformationUiState(
    val isLoading: Boolean = false,
    val infoMessage: String = "Bienvenido a la pantalla de Información",
    val errorMessage: String? = null
)