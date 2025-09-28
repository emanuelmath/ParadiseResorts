//Este archivo contiene la definición del VM respectivo de la pantalla de Servicios extras
package com.example.paradiseresorts.ui.screens.services

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.ui.classes.ServicesUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ServicesViewModel(/*Introducir repositorios necesarios*/) : ViewModel() {

    //Etiqueta para filtrar logs referentes a este VM:
    companion object {
        private const val TAG = "servicesVM"
    }

    var uiState: ServicesUiState by mutableStateOf(ServicesUiState())
        private set

    //Función de carga de servicios que ofrecemos:
    fun loadServices() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            delay(1000) // Simula llamada a BD o API
            uiState = uiState.copy(
                isLoading = false,
                message = "Servicios cargados correctamente"
            )
            Log.d(TAG, "Servicios cargados")
        }
    }
}