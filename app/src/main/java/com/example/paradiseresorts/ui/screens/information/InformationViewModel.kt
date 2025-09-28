//Este archivo contiene la definición del ViewModel respectivo de InformationScreen
package com.example.paradiseresorts.ui.screens.information

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.ui.classes.InformationUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class InformationViewModel(/*Introducir repositorios necesarios*/) : ViewModel() {

    //Etiqueta de filtrado de logs referentes a este VM:
    companion object {
        private const val TAG = "informationVM"
    }

    var uiState: InformationUiState by mutableStateOf(InformationUiState())
        private set

    //Función de ejemplo para refrescar información de la pantalla:
    fun refreshInformation() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000) // Simulación de carga
            uiState = uiState.copy(
                isLoading = false,
                infoMessage = "Información actualizada"
            )
            Log.d(TAG, "Información actualizada")
        }
    }
}
