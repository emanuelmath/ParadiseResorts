package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("service", foreignKeys = [
    ForeignKey(UserEntity::class, parentColumns = ["dui"], childColumns = ["dui"], onDelete = ForeignKey.CASCADE)
])
data class ServiceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val price: Double,
    val dui: String
)