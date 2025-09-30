//Archivo con la definición del VMFactory para pasar los repositorios como parámetro al VM
package com.example.paradiseresorts.ui.screens.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.paradiseresorts.data.database.ParadiseResortsApplication
import com.example.paradiseresorts.data.repository.ServiceRepository
import com.example.paradiseresorts.data.repository.TransactionRepository

class ServicesViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServicesViewModel::class.java)) {
            val serviceRepository = ServiceRepository(ParadiseResortsApplication.database.ServiceDao())
            val transactionRepository = TransactionRepository(ParadiseResortsApplication.database.TransactionDao())
            return ServicesViewModel(serviceRepository, transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}