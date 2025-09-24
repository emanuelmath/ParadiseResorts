package com.example.paradiseresorts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paradiseresorts.data.database.dao.*
import com.example.paradiseresorts.data.database.entities.*

@Database(entities = [CardEntity::class, HotelEntity::class, ReservationEntity::class,
    RoomEntity::class, ServiceEntity::class, SessionEntity::class, TransactionEntity::class,
    UserEntity::class], version = 1)
abstract class ParadiseResortsDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
    abstract fun HotelDao(): HotelDao
    abstract fun ReservationDao(): ReservationDao
    abstract fun RoomDao(): RoomDao
    abstract fun ServiceDao(): ServiceDao
    abstract fun SessionDao(): SessionDao
    abstract fun TransactionDao(): TransactionDao
    abstract fun UserDao(): UserDao
}