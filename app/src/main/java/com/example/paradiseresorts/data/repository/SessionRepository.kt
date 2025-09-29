package com.example.paradiseresorts.data.repository

import com.example.paradiseresorts.data.database.dao.SessionDao
import com.example.paradiseresorts.domain.mappers.toEntity
import com.example.paradiseresorts.domain.mappers.toModel
import com.example.paradiseresorts.domain.models.Session

class SessionRepository(private val sessionDao: SessionDao) {
    suspend fun createSession(session: Session): Long {
        return sessionDao.createSession(session.toEntity())
    }

    suspend fun deleteAllSessions() {
        sessionDao.deleteAllSessions()
    }

    suspend fun obtainCurrentSession(): Session? {
        return sessionDao.obtainCurrentSession()?.toModel()
    }
}