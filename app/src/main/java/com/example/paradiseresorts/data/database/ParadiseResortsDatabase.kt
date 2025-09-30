package com.example.paradiseresorts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paradiseresorts.data.database.dao.*
import com.example.paradiseresorts.data.database.entities.*

@Database(entities = [CardEntity::class, FeedbackEntity::class,HotelEntity::class, ReservationEntity::class,
    RoomEntity::class, ServiceEntity::class, SessionEntity::class, TransactionEntity::class,
    UserEntity::class], version = 3) //1- Versión Inicial de la BD. 2- Algunos campos unique y se agregó la tabla feedback. 3- Cambios en la capa de datos
abstract class ParadiseResortsDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
    abstract fun FeedbackDao(): FeedbackDao
    abstract fun HotelDao(): HotelDao
    abstract fun ReservationDao(): ReservationDao
    abstract fun RoomDao(): RoomDao
    abstract fun ServiceDao(): ServiceDao
    abstract fun SessionDao(): SessionDao
    abstract fun TransactionDao(): TransactionDao
    abstract fun UserDao(): UserDao
}