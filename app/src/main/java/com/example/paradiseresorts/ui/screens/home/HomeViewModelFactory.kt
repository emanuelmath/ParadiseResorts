//Archivo que contiene la clase VMFactory para inyectar repositorios como parámetros al VM
package com.example.paradiseresorts.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeContentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeContentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeContentViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}