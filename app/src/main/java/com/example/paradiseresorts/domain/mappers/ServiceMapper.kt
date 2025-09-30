package com.example.paradiseresorts.domain.mappers

import com.example.paradiseresorts.data.database.entities.ServiceEntity
import com.example.paradiseresorts.domain.models.Service

fun Service.toEntity(): ServiceEntity {
    return ServiceEntity(
        id = id,
        nombre = nombre,
        price = price,
        dui = dui,
        isActive = isActive
    )
}

fun ServiceEntity.toModel(): Service {
    return Service(
        id = id,
        nombre = nombre,
        price = price,
        dui = dui,
        isActive = isActive
    )
}