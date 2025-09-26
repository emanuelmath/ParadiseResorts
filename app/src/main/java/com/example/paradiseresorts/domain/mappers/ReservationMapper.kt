package com.example.paradiseresorts.domain.mappers
import com.example.paradiseresorts.data.database.entities.ReservationEntity
import com.example.paradiseresorts.domain.models.Reservation

fun Reservation.toEntity(): ReservationEntity {
    return ReservationEntity(
        id = id,
        roomId = roomId,
        dui = dui,
        entryDate = entryDate,
        expirationDate = expirationDate
    )
}

fun ReservationEntity.toModel(): Reservation {
    return Reservation(
        id = id,
        roomId = roomId,
        dui = dui,
        entryDate = entryDate,
        expirationDate = expirationDate
    )
}