//Archivo con la definición del VMFactory para inyectar repositorios como parámetros en el VM
package com.example.paradiseresorts.ui.screens.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FeedbackViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FeedbackViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
