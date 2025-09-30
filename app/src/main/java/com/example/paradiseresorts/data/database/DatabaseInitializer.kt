package com.example.paradiseresorts.data.database

import com.example.paradiseresorts.ui.classes.CatalogProvider

object DatabaseInitializer {

    suspend fun seedDatabase(database: ParadiseResortsDatabase) {
        val hotelDao = database.HotelDao()
        val roomDao = database.RoomDao()

        // Insertar hoteles si no existen
        if (hotelDao.getAllHotels().isNullOrEmpty()) {
            CatalogProvider.hotels.forEach { hotel ->
                hotelDao.insertHotel(hotel)
            }
        }

        // Insertar rooms si no existen
        if (roomDao.getAllRooms().isNullOrEmpty()) {
            CatalogProvider.rooms.forEach { room ->
                roomDao.insertRoom(room)
            }
        }
    }
}
