package com.example.paradiseresorts.domain.mappers
import com.example.paradiseresorts.data.database.entities.RoomEntity
import com.example.paradiseresorts.domain.models.Room

fun Room.toEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        name = name,
        price = price,
        hotelId = id,
        category = category,
        isReserved = isReserved
    )
}

fun RoomEntity.toModel(): Room {
    return Room(
        id = id,
        name = name,
        price = price,
        hotelId = id,
        category = category,
        isReserved = isReserved
    )
}