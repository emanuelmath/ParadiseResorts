package com.example.paradiseresorts.domain.models

data class Room(
    val id: Int, //Como los creamos nosotros, no tiene ID autoincremental.
    val name: String,
    val price: Double,
    val hotelId: Int,
    val category: String,
    var isReserved: Boolean = false
)
