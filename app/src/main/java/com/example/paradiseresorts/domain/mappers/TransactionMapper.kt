package com.example.paradiseresorts.domain.mappers

import com.example.paradiseresorts.data.database.entities.TransactionEntity
import com.example.paradiseresorts.domain.models.Transaction

fun Transaction.toEntity(): TransactionEntity {
    return TransactionEntity(
        id = id,
        dui = dui,
        transactionDate = transactionDate,
        acquiredService = acquiredService,
        amount = amount
    )
}

fun TransactionEntity.toModel(): Transaction {
    return Transaction(
        id = id,
        dui = dui,
        transactionDate = transactionDate,
        acquiredService = acquiredService,
        amount = amount
    )
}