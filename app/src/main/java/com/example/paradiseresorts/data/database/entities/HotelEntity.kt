package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("hotel")
data class HotelEntity(
    @PrimaryKey(false)
    val id: Int = 0,
    val name: String
) {
}