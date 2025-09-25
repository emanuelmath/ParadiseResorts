package com.example.paradiseresorts.domain.mappers

import com.example.paradiseresorts.data.database.entities.UserEntity
import com.example.paradiseresorts.domain.models.User

fun UserEntity.toModel(): User {
    return User(
        dui = dui,
        name = name,
        lastName = lastName,
        dateOfBirthday = dateOfBirthday,
        email = email,
        password = password,
        phoneNumber = phoneNumber,
        balance = balance,
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        dui = dui,
        name = name,
        lastName = lastName,
        dateOfBirthday = dateOfBirthday,
        email = email,
        password = password,
        phoneNumber = phoneNumber,
        balance = balance,
    )
}