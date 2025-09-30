package com.example.paradiseresorts.domain.models

data class Service(
    val id: Int = 0,
    val nombre: String,
    val price: Double,
    val dui: String,
    val isActive: Boolean = true
)