package com.example.paradiseresorts.ui.classes

data class CardInfo(
    val code: String,
    val dui: String,
    val expirationDate: String,
    val cvv: String,
    var balance: Double = 2000.00
)