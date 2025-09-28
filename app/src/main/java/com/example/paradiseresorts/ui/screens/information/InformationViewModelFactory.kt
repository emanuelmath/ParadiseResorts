//Archivo con la definición del VMFactory para inyectar repositorios como parámetros en el VM
package com.example.paradiseresorts.ui.screens.information

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InformationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InformationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}