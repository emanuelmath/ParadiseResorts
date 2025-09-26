package com.example.paradiseresorts.domain.mappers

import com.example.paradiseresorts.data.database.entities.HotelEntity
import com.example.paradiseresorts.domain.models.Hotel

fun Hotel.toEntity(): HotelEntity {
    return HotelEntity(
        id = id,
        name = name
    )
}

fun HotelEntity.toModel(): Hotel {
    return Hotel(
        id = id,
        name = name
    )
}