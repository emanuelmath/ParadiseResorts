package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` WHERE dui = :dui")
    suspend fun getAllTransactionUser(dui: String): List<TransactionEntity>?

    @Insert
    suspend fun createTransaction(transaction: TransactionEntity): Long
}