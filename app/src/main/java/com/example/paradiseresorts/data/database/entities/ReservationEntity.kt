package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("reservation", foreignKeys = [
    ForeignKey(RoomEntity::class, parentColumns = ["id"], childColumns = ["roomId"], onDelete = ForeignKey.CASCADE),
    ForeignKey(UserEntity::class, parentColumns = ["dui"], childColumns = ["dui"], onDelete = ForeignKey.CASCADE),
    ForeignKey(HotelEntity::class, parentColumns = ["id"], childColumns = ["hotelId"])
])
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val roomId: Int,
    val hotelId: Int,
    val dui: String,
    val entryDate: String,
    val expirationDate: String
)