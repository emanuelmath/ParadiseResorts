package com.example.paradiseresorts.data.database

import android.app.Application
import androidx.room.Room

class ParadiseResortsApplication: Application() {
    companion object {
        private var _database: ParadiseResortsDatabase? = null
        val database: ParadiseResortsDatabase get() {
            return _database ?: throw IllegalArgumentException("No se inicializó la base de datos.")
        }

    }

    override fun onCreate() {
        super.onCreate()
        if(_database == null) {
            _database = Room.databaseBuilder(
                this,
                ParadiseResortsDatabase::class.java,
                "ParadiseResorts"
            ).fallbackToDestructiveMigration().build()
        }
    }
}