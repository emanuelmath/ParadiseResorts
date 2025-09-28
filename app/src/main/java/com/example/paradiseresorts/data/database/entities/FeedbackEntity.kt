package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("feedback", foreignKeys = [ForeignKey(RoomEntity::class, parentColumns = ["id"], childColumns = ["roomId"], onDelete = ForeignKey.CASCADE),
    ForeignKey(UserEntity::class, parentColumns = ["dui"], childColumns = ["dui"], onDelete = ForeignKey.CASCADE),
    ForeignKey(HotelEntity::class, parentColumns = ["id"], childColumns = ["hotelId"])])
data class FeedbackEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var name: String,
    var lastName: String,
    val opinion: String,
    val rate: Int,
    val dui: String,
    val roomId: Int,
    val hotelId: Int
    )