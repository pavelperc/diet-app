package com.example.dietapp.domain

data class Product(
    val id: Long,
    val name: String,
    val nFactor: Double,
    val proFactor: Double,
    val fatFactor: Double,
    val choFactor: Double
)