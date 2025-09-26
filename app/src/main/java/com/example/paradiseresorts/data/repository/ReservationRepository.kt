package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.ReservationDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Reservation

class ReservationRepository(private val reservationDao: ReservationDao) {

    suspend fun getAllReservationByUserDUI(dui: String, todayDate: String): List<Reservation>? {
        return reservationDao.getAllReservationByUserDUI(dui, todayDate)?.map { it.toModel() }
    }

    suspend fun createReservation(reservation: Reservation): Long {
        return reservationDao.createReservation(reservation.toEntity())
    }
}