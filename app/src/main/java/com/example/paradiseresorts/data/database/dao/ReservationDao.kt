package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.ReservationEntity

@Dao
interface ReservationDao {
    @Query("SELECT * FROM reservation WHERE dui = :dui AND DATE(expirationDate) > DATE(:todayDate)")
    suspend fun getAllReservationByUserDUI(dui: String, todayDate: String): List<ReservationEntity>?

    @Insert
    suspend fun createReservation(reservation: ReservationEntity): Long


}