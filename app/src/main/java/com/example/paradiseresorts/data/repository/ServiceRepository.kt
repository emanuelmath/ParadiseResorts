package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.ServiceDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Service

class ServiceRepository(private val serviceDao: ServiceDao) {

    suspend fun getAllServices(): List<Service>? {
        return serviceDao.getAllServices()?.map { it.toModel() }
    }

    suspend fun insertService(service: Service): Long {
        return serviceDao.insertService(service.toEntity())
    }

    suspend fun deleteAllService() {
        serviceDao.deleteAllService()
    }
}