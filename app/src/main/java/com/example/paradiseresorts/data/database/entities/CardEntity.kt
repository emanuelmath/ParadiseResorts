package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.paradiseresorts.data.database.entities.*

@Entity("card", foreignKeys = [ForeignKey(
    entity = UserEntity::class,
    parentColumns = ["dui"],
    childColumns = ["dui"],
    onDelete = ForeignKey.CASCADE
)])
data class CardEntity(
    @PrimaryKey
    val code: String,
    val expirationDate: String,
    val cvv: String,
    val dui: String,
    var balance: Double = 2000.00
)