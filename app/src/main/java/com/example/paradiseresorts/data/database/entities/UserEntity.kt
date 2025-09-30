package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("user", indices = [
    Index(value = ["email"], unique = true),
    Index(value = ["phoneNumber"], unique = true)])
data class UserEntity(
    @PrimaryKey
    val dui: String,
    var name: String,
    var lastName: String,
    val dateOfBirthday: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    var balance: Double = 0.0
) {
}