package com.example.paradiseresorts.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.SessionRepository

class SplashViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(sessionRepository = SessionRepository(ParadiseResortsApplication.database.SessionDao()))
                    as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}