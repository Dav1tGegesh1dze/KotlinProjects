package com.example.ponti

import com.example.ponti.domain.Category
import com.example.ponti.domain.Event


fun EventDto.toDomain(): Event {
    return Event(
        id = id,
        title = title,
        category = category,
        startPrice = startPrice,
        endPrice = endPrice,
        date = date,
        location = location,
        description = description,
        imageUrl = imageUrl
    )
}

fun PontiResponse.toCategoryList(): List<Category> {
    return categories.map { Category(it) }
}

fun PontiResponse.toEventList(): List<Event> {
    return events.map { it.toDomain() }
}