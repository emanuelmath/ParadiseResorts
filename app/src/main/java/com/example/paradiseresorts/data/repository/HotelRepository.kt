package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.HotelDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Hotel

class HotelRepository(private val hotelDao: HotelDao) {
    suspend fun getAllHotels(): List<Hotel>? {
        return hotelDao.getAllHotels()?.map { it.toModel() }
    }

    suspend fun insertHotel(hotel: Hotel): Long {
        return hotelDao.insertHotel(hotel.toEntity())
    }

    suspend fun deleteAllHotels() {
        hotelDao.deleteAllHotels()
    }
}