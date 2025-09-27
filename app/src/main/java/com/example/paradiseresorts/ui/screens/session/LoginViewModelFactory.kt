package com.example.paradiseresorts.ui.screens.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(
    // Más adelante: private val loginRepository: LoginRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                //Introducir el loginRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
