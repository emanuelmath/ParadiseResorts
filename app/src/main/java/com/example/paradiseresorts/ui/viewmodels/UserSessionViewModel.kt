package com.example.paradiseresorts.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.repository.SessionRepository
import com.example.paradiseresorts.ui.classes.UserSessionState
import kotlinx.coroutines.launch

class UserSessionViewModel(private val sessionRepository: SessionRepository): ViewModel() {
    var duiState by mutableStateOf(UserSessionState())
        private set

//    init {
//        viewModelScope.launch {
//            val dui = sessionRepository.obtainCurrentSession()?.dui
//            duiState = duiState.copy(
//                dui = dui
//            )
//            Log.d("USVM", "$dui y ${duiState.dui} actual")
//        }
//    }
    fun loadSession() {
        viewModelScope.launch {
            val session = sessionRepository.obtainCurrentSession()
            duiState = duiState.copy(dui = session?.dui)
        }
    }


    /*fun updateDui(newDui: String) {
        duiState = duiState.copy(
            dui = newDui
        )
    }*/
}