package com.example.paradiseresorts.data.database

import android.app.Application
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ParadiseResortsApplication : Application() {

    companion object {
        private var _database: ParadiseResortsDatabase? = null
        val database: ParadiseResortsDatabase
            get() = _database ?: throw IllegalArgumentException("No se inicializó la base de datos.")
    }

    override fun onCreate() {
        super.onCreate()
        if (_database == null) {
            _database = Room.databaseBuilder(
                this,
                ParadiseResortsDatabase::class.java,
                "ParadiseResorts"
            ).fallbackToDestructiveMigration()
                .build()

            // Poblar la base de datos con datos iniciales
            GlobalScope.launch(Dispatchers.IO) {
                _database?.let { DatabaseInitializer.seedDatabase(it) }
            }
        }
    }
}