package com.example.paradiseresorts.domain.models

data class Room(
    val id: Int,
    val name: String,
    val price: Double,
    val hotelId: Int,
    val category: String,
    var isReserved: Boolean = false
)
