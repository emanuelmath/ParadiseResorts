package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.RoomEntity

@Dao
interface RoomDao {
    @Query("SELECT * FROM room")
    suspend fun getAllRooms(): List<RoomEntity>?

    @Query("SELECT * FROM room WHERE isReserved = 0")
    suspend fun getAllRoomsAvailable(): List<RoomEntity>?

    @Query("UPDATE room SET isReserved = 0 WHERE id = :id")
    suspend fun setRoomTAvailable(id: Int)

    @Insert
    suspend fun insertRoom(room: RoomEntity): Long

    @Query("DELETE FROM room")
    suspend fun deleteAllRooms()
}