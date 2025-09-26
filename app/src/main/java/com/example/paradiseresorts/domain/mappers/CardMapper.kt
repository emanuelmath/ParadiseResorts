package com.example.paradiseresorts.domain.mappers
import com.example.paradiseresorts.data.database.entities.CardEntity
import com.example.paradiseresorts.domain.models.Card

fun Card.toEntity(): CardEntity {
    return CardEntity(
        code = code,
        expirationDate = expirationDate,
        cvv = cvv,
        dui = dui,
        balance = balance
    )
}

fun CardEntity.toModel(): Card {
    return Card(
        code = code,
        expirationDate = expirationDate,
        cvv = cvv,
        dui = dui,
        balance = balance
    )
}
