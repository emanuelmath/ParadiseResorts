package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.TransactionDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Transaction

class TransactionRepository(private val transactionDao: TransactionDao) {

    suspend fun getAllTransactionUser(dui: String): List<Transaction>? {
        return transactionDao.getAllTransactionUser(dui)?.map { it.toModel() }
    }

    suspend fun createTransaction(transaction: Transaction): Long {
        return transactionDao.createTransaction(transaction.toEntity())
    }
}