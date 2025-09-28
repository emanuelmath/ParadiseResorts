package com.example.paradiseresorts.ui.screens.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.database.ParadiseResortsDatabase
import com.example.paradiseresorts.data.database.dao.UserDao
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.data.repository.UserRepository

class LoginViewModelFactory(
    // Más adelante: private val loginRepository: LoginRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
               userRepository = UserRepository(ParadiseResortsApplication.database.UserDao()),
                sessionRepository = SessionRepository(ParadiseResortsApplication.database.SessionDao())
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
