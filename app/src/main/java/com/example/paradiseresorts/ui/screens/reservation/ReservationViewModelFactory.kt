//Este archivo contiene la clase VMFactory para implementar los repositories como parámetros de VM
package com.example.paradiseresorts.ui.screens.reservation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ReservationViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReservationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReservationViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}