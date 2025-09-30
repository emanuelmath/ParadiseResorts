package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.ServiceDao
import com.example.paradiseresorts.data.database.entities.ServiceEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Service

class ServiceRepository(private val serviceDao: ServiceDao) {

    suspend fun getAllServices(): List<Service>? {
        return serviceDao.getAllServices()?.map { it.toModel() }
    }

    suspend fun getAllServicesByDUI(dui: String): List<Service>? {
        return serviceDao.getAllServicesByDUI(dui)?.map { it.toModel() }
    }

    suspend fun insertService(service: Service, userDui: String = ""): Long {
        val entity = ServiceEntity(
            nombre = service.nombre,
            price = service.price,
            dui = userDui,
            isActive = true
        )
        return serviceDao.insertService(entity)
    }

    suspend fun deleteAllService() {
        serviceDao.deleteAllService()
    }
}