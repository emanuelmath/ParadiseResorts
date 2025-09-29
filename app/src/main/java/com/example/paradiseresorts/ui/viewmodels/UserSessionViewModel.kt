package com.example.paradiseresorts.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.SessionRepository
import kotlinx.coroutines.launch

class UserSessionViewModel(private val sessionRepository: SessionRepository): ViewModel() {
    var dui by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            dui = sessionRepository.obtainCurrentSession()?.dui
        }
    }
}