package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.CardDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Card

class CardRepository(private val cardDao: CardDao) {
    suspend fun createCard(card: Card): Long {
        return cardDao.createCard(card.toEntity())
    }

    suspend fun verifyCard(code: String, cvv: Int, dui: String): Card? {
        return cardDao.verifyCard(code, cvv, dui)?.toModel()
    }

    suspend fun verifyCurrentCardIsAvailable(todayDate: String): String? {
        return cardDao.verifyCurrentCardIsAvailable(todayDate)
    }
}