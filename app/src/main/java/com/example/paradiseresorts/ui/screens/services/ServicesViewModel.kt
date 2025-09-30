//Este archivo contiene la definición del VM respectivo de la pantalla de Servicios extras
package com.example.paradiseresorts.ui.screens.services

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paradiseresorts.data.database.entities.ServiceEntity
import com.example.paradiseresorts.data.repository.ServiceRepository
import com.example.paradiseresorts.data.repository.TransactionRepository
import com.example.paradiseresorts.domain.models.Service
import com.example.paradiseresorts.domain.models.Transaction
import com.example.paradiseresorts.ui.classes.CatalogProvider
import com.example.paradiseresorts.ui.classes.ServicesUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ServicesViewModel(
    private val serviceRepository: ServiceRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    companion object {
        private const val TAG = "servicesVM"
    }

    var uiState: ServicesUiState by mutableStateOf(ServicesUiState())
        private set

    // Cargar catálogo (desde provider)
    fun loadCatalogServices() {
        uiState = uiState.copy(
            offeredServices = CatalogProvider.services
        )
        Log.d(TAG, "Catálogo de servicios cargado")
    }

    // Cargar servicios contratados por usuario
    fun loadUserServices(dui: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)

                val services = serviceRepository.getAllServicesByDUI(dui) ?: emptyList()

                uiState = uiState.copy(isLoading = false, userServices = services)
                Log.d(TAG, "Servicios del usuario cargados")
            } catch (e: Exception) {
                uiState = uiState.copy(isLoading = false, error = e.message)
            }
        }
    }

    // Contratar un servicio y registrar transacción
    fun acquireService(service: Service, dui: String) {
        viewModelScope.launch {
            try {
                serviceRepository.insertService(service.copy(dui = dui))

                val transaction = Transaction(
                    dui = dui,
                    acquiredService = service.nombre,
                    transactionDate = getTodayDate(),
                    amount = service.price
                )
                transactionRepository.createTransaction(transaction)

                Log.d(TAG, "Servicio contratado y transacción creada")
            } catch (e: Exception) {
                Log.e(TAG, "Error al contratar servicio", e)
            }
        }
    }

    private fun getTodayDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formatter.format(Date())
    }

    fun insertCatalogServicesToDB(dui: String = "") {
        viewModelScope.launch {
            CatalogProvider.services.forEach { service ->
                try {
                    // Creamos un Service con DUI
                    val serviceToInsert = service.copy(dui = dui)
                    serviceRepository.insertService(serviceToInsert)
                    Log.d(TAG, "Servicio insertado: ${service.nombre}")
                } catch (e: Exception) {
                    Log.e(TAG, "Error insertando servicio ${service.nombre}", e)
                }
            }
        }
    }

    // Función de extensión para convertir Service -> ServiceEntity
    fun Service.toEntity(userDui: String): ServiceEntity {
        return ServiceEntity(
            nombre = this.nombre,
            price = this.price,
            dui = userDui,
            isActive = true
        )
    }
}