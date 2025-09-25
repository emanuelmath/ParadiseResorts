package com.example.paradiseresorts.domain.models

data class User(
    val dui: String,
    var name: String,
    var lastName: String,
    val dateOfBirthday: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    var balance: Double
) {
}