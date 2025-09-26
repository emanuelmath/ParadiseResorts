package com.example.paradiseresorts.domain.mappers

import com.example.paradiseresorts.data.database.entities.SessionEntity
import com.example.paradiseresorts.domain.models.Session

fun Session.toEntity(): SessionEntity {
    return SessionEntity(
        id = id,
        dui = dui,
        sessionDate = sessionDate
    )
}

fun SessionEntity.toModel(): Session {
    return Session(
        id = id,
        dui = dui,
        sessionDate = sessionDate
    )
}