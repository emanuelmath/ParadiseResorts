package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.CardEntity
import com.example.paradiseresorts.domain.mappers.toEntity

    @Dao
    interface CardDao {
        @Insert
        suspend fun createCard(card: CardEntity): Long

        @Query("SELECT * FROM card WHERE code = :code ")
        suspend fun getCardByCode(code: String): CardEntity?
        @Query("SELECT * FROM card WHERE dui = :dui")
        suspend fun getCardByDUI(dui: String): CardEntity?

        @Query("SELECT * FROM card WHERE code = :code AND cvv = :cvv AND dui = :dui")
        suspend fun verifyCard(code: String, cvv: Int, dui: String): CardEntity?

        @Query("SELECT code FROM card WHERE DATE(expirationDate) < DATE(:todayDate) AND code = :code")
        suspend fun verifyCurrentCardIsAvailable(todayDate: String, code: String): String?

        @Query("UPDATE card SET balance = :newBalance WHERE dui = :dui")
        suspend fun updateCardBalance(dui: String, newBalance: Double)
    }