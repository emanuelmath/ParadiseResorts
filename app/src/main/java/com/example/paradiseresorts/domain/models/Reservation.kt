package com.example.paradiseresorts.domain.models

data class Reservation(
    val id: Int = 0, //Para que no pida ID en la conversión de model a entity, de eso se encarga room.
    val roomId: Int,
    val dui: String,
    val entryDate: String,
    val expirationDate: String
)