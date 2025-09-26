package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.SessionEntity

@Dao
interface SessionDao {

    @Insert
    suspend fun createSession(session: SessionEntity): Long

    @Query("DELETE FROM session WHERE dui = :dui")
    suspend fun deleteSessionUser(dui: String)

}