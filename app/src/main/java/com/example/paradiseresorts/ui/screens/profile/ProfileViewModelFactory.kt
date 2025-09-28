//Este archivo contiene la definición del VMFactory para los parámetros del ProfileVM
package com.example.paradiseresorts.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.SessionRepository

class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                //Solo es para evitar errores, si necesitas otro repository lo editas:
                sessionRepository = SessionRepository(ParadiseResortsApplication.database.SessionDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}