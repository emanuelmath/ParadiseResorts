package com.example.paradiseresorts.domain.models

data class Card(
    val code: String,
    val expirationDate: String,
    val cvv: String,
    val dui: String,
    var balance: Double = 2000.00
) {
}