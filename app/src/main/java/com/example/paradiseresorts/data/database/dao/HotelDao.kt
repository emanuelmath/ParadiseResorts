package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.HotelEntity

@Dao
interface HotelDao {
    @Query("SELECT * FROM hotel")
    suspend fun getAllHotels(): List<HotelEntity>?

    @Insert
    suspend fun insertHotel(hotel: HotelEntity): Long

    @Query("DELETE FROM hotel")
    suspend fun deleteAllHotels()
}