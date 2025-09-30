package com.example.paradiseresorts.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.paradiseresorts.data.database.entities.FeedbackEntity

@Dao
interface FeedbackDao {

    // Crear (Insertar un nuevo feedback)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedback(feedback: FeedbackEntity): Long

    // Leer todos los feedbacks
    @Query("SELECT * FROM feedback")
    suspend fun getAllFeedbacks(): List<FeedbackEntity>

    // Leer feedback por ID
    @Query("SELECT * FROM feedback WHERE id = :id")
    suspend fun getFeedbackById(id: Int): FeedbackEntity?

    // Leer feedbacks por DUI del usuario
    @Query("SELECT * FROM feedback WHERE dui = :dui")
    suspend fun getFeedbacksByUser(dui: String): List<FeedbackEntity>

    // Actualizar un feedback existente
    @Update
    suspend fun updateFeedback(feedback: FeedbackEntity)

    // Eliminar un feedback
    @Delete
    suspend fun deleteFeedback(feedback: FeedbackEntity)
}