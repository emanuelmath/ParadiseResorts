package com.example.paradiseresorts.domain.models

data class Reservation(
    val id: Int,
    val roomId: Int,
    val dui: String,
    val entryDate: String,
    val expirationDate: String
)