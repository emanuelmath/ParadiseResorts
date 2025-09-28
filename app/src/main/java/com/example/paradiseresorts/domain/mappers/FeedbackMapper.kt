package com.example.paradiseresorts.domain.mappers

import com.example.paradiseresorts.data.database.entities.FeedbackEntity
import com.example.paradiseresorts.domain.models.Feedback

fun Feedback.toEntity(): FeedbackEntity {
    return FeedbackEntity(
        name = name,
        lastName = lastName,
        opinion = opinion,
        rate = rate,
        dui = dui,
        roomId = roomId,
        hotelId = hotelId
    )
}

fun FeedbackEntity.toModel(): Feedback {
    return Feedback(
        name = name,
        lastName = lastName,
        opinion = opinion,
        rate = rate,
        dui = dui,
        roomId = roomId,
        hotelId = hotelId
    )
}