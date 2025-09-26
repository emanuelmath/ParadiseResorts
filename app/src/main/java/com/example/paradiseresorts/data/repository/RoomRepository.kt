package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.RoomDao
import com.example.paradiseresorts.domain.models.Room
import com.example.paradiseresorts.domain.mappers.*

class RoomRepository(private val roomDao: RoomDao) {

    suspend fun getAllRooms(): List<Room>? {
        return roomDao.getAllRooms()?.map { it.toModel() }
    }

    suspend fun getAllRoomsAvailable(): List<Room>? {
        return roomDao.getAllRoomsAvailable()?.map { it.toModel() }
    }

    suspend fun setRoomAvailable(id: Int) {
        roomDao.setRoomAvailable(id)
    }

    suspend fun setRoomNotAvailable(id: Int) {
        roomDao.setRoomNotAvailable(id)
    }

    suspend fun insertRoom(room: Room): Long {
        return roomDao.insertRoom(room.toEntity())
    }

    suspend fun deleteAllRooms() {
        roomDao.deleteAllRooms()
    }

}