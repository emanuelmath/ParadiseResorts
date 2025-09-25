package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("session",
    foreignKeys = [ForeignKey(UserEntity::class, parentColumns = ["dui"], childColumns = ["dui"], onDelete = ForeignKey.CASCADE)])
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dui: String,
    val sessionDate: String
)