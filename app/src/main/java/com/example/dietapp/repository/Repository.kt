package com.example.dietapp.repository

import com.example.dietapp.domain.Diet
import com.example.dietapp.domain.Dish
import com.example.dietapp.domain.Product

interface Repository {
    suspend fun findProducts(query: String): List<Product>
    suspend fun findDishes(query: String): List<Dish> = allDishes().filter { it.name.contains(query, true) }
    suspend fun findDiets(query: String): List<Diet> = allDiets().filter { it.name.contains(query, true) }
    suspend fun allDishes(): List<Dish>
    suspend fun allDiets(): List<Diet>

    class HttpError(error: String) : RuntimeException(error)
}