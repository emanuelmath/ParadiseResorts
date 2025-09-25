package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.CardEntity

@Dao
interface CardDao {
    @Insert
    suspend fun createCard(card: CardEntity): Long

    @Query("SELECT * FROM card WHERE code = :code AND cvv = :cvv AND dui = :dui")
    suspend fun verifyCard(code: String, cvv: Int, dui: String): CardEntity?

    @Query("SELECT code FROM card WHERE DATE(expirationDate) < DATE(:todayDate)")
    suspend fun verifyCurrentCardIsAvailable(todayDate: String): String?
}