package com.example.paradiseresorts.domain.models

data class Feedback(
    val id: Int = 0,
    var name: String,
    var lastName: String,
    val opinion: String,
    val rate: Int,
    val dui: String,
    val roomId: Int,
    val hotelId: Int
) {
}