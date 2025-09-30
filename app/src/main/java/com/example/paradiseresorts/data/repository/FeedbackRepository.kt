package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.FeedbackDao
import com.example.paradiseresorts.data.database.entities.FeedbackEntity

class FeedbackRepository(private val feedbackDao: FeedbackDao) {

    // Crear o insertar un feedback
    suspend fun addFeedback(feedback: FeedbackEntity): Long {
        return feedbackDao.insertFeedback(feedback)
    }

    // Obtener todos los feedbacks
    suspend fun getAllFeedbacks(): List<FeedbackEntity> {
        return feedbackDao.getAllFeedbacks()
    }

    // Obtener un feedback por ID
    suspend fun getFeedbackById(id: Int): FeedbackEntity? {
        return feedbackDao.getFeedbackById(id)
    }

    // Obtener todos los feedbacks de un usuario por DUI
    suspend fun getFeedbacksByUser(dui: String): List<FeedbackEntity> {
        return feedbackDao.getFeedbacksByUser(dui)
    }

    // Actualizar un feedback existente
    suspend fun updateFeedback(feedback: FeedbackEntity) {
        feedbackDao.updateFeedback(feedback)
    }

    // Eliminar un feedback
    suspend fun deleteFeedback(feedback: FeedbackEntity) {
        feedbackDao.deleteFeedback(feedback)
    }
}
