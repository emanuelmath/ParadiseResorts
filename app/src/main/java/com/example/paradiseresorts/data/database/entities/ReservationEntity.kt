package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("reservation", foreignKeys = [
    ForeignKey(RoomEntity::class, parentColumns = ["id"], childColumns = ["roomId"]),
    ForeignKey(UserEntity::class, parentColumns = ["dui"], childColumns = ["dui"])
])
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val roomId: Int,
    val dui: String,
    val entryDate: String,
    val expirationDate: String
)