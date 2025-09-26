package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.ServiceEntity

@Dao
interface ServiceDao {

    @Query("SELECT * FROM service")
    suspend fun getAllServices(): List<ServiceEntity>?

    @Insert
    suspend fun insertService(service: ServiceEntity): Long

    @Query("DELETE FROM service")
    suspend fun deleteAllService()

}