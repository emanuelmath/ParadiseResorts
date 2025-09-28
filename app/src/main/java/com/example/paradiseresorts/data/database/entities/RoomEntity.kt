package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("room")
data class RoomEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 0,
    val name: String,
    val price: Double,
    val hotelId: Int,
    val category: String,
    var isReserved: Boolean = false
)