package com.example.paradiseresorts.domain.models

data class Transaction(
    val id: Int = 0,
    val dui: String,
    val transactionDate: String,
    val acquiredService: String,
    val amount: Double
) {
}