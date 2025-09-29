package com.example.paradiseresorts.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.SessionRepository

class UserSessionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserSessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserSessionViewModel(sessionRepository = SessionRepository(ParadiseResortsApplication.database.SessionDao()))
                    as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}