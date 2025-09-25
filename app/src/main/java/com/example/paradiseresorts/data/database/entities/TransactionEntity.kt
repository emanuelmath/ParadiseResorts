package com.example.paradiseresorts.data.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("transaction", foreignKeys = [
    ForeignKey(UserEntity::class, parentColumns = ["dui"], childColumns = ["dui"], onDelete = ForeignKey.CASCADE)
])
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dui: String,
    val transactionDate: String,
    val amount: Double
) {
}