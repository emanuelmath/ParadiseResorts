package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.UserDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.User

class UserRepository(private val userDao: UserDao) {

    suspend fun createUser(user: User): Long {
        return userDao.createUser(user.toEntity())
    }

    suspend fun getUserByDUI(dui: String): User? {
        return userDao.getUserByDUI(dui)?.toModel()
    }

    suspend fun getUserByEmail(email: String): User? {
        return userDao.getUserByEmail(email)?.toModel()
    }

    suspend fun getUserByPhoneNumber(phoneNumber: String): User? {
        return userDao.getUserByPhoneNumber(phoneNumber)?.toModel()
    }

    suspend fun updateUserBalance(dui: String, newBalance: Double) {
        userDao.updateUserBalance(dui, newBalance)
    }
}