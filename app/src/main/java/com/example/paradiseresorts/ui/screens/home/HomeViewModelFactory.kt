//Archivo que contiene la clase VMFactory para inyectar repositorios como parámetros al VM
package com.example.paradiseresorts.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.UserRepository

class HomeContentViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeContentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeContentViewModel(UserRepository(ParadiseResortsApplication.database.UserDao())) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}