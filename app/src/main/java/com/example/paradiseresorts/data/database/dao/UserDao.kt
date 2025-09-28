package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.paradiseresorts.data.database.entities.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE dui = :dui")
    suspend fun getUserByDUI(dui: String): UserEntity?
    @Query("SELECT * FROM user WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserEntity?
    @Query("SELECT * FROM user WHERE phoneNumber = :phoneNumber")
    suspend fun getUserByPhoneNumber(phoneNumber: String): UserEntity?
    @Insert
    suspend fun createUser(user: UserEntity): Long

}