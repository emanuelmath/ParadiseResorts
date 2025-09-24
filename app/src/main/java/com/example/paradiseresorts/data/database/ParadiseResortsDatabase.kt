package com.example.paradiseresorts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.paradiseresorts.data.database.entities.*

@Database(entities = [CardEntity::class, HotelEntity::class, ReservationEntity::class,
    RoomEntity::class, ServiceEntity::class, TransactionEntity::class,
    UserEntity::class], version = 1)
abstract class ParadiseResortsDatabase : RoomDatabase() {
 //Meter daos.
}