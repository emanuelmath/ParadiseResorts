//Archivo con la definición del VMFactory para pasar los repositorios como parámetro al VM
package com.example.paradiseresorts.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ServicesViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ServicesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}