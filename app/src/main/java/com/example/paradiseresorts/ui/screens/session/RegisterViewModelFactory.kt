//Este archivo contiene la definición del VMFactory de registro para los parámetros de los VM.
package com.example.paradiseresorts.ui.screens.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.CardRepository
import com.example.paradiseresorts.data.repository.UserRepository

//ViewModelFactory sujeto a cambios al añadir los repositorios.
class RegisterViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegisterViewModel(userRepository = UserRepository(ParadiseResortsApplication.database.UserDao())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}