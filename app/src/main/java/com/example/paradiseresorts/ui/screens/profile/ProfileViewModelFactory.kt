//Este archivo contiene la definición del VMFactory para los parámetros del ProfileVM
package com.example.paradiseresorts.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.CardRepository
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository

class ProfileViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(
                userRepository = UserRepository(ParadiseResortsApplication.database.UserDao()),
                sessionRepository = SessionRepository(ParadiseResortsApplication.database.SessionDao()),
                cardRepository = CardRepository(ParadiseResortsApplication.database.CardDao())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



/*
class ProfileViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(
                userRepository = UserRepository(ParadiseResortsApplication.database.UserDao()),
                sessionRepository = SessionRepository(ParadiseResortsApplication.database.SessionDao()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/