package com.example.paradiseresorts.domain.models

data class Transaction(
    val id: Int,
    val dui: String,
    val transactionDate: String,
    val amount: Double
) {
}