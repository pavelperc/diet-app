package com.example.dietapp.repository

import com.example.dietapp.domain.Diet
import com.example.dietapp.domain.Dish
import com.example.dietapp.domain.Product
import kotlinx.coroutines.delay

class TestRepository: Repository {

    override suspend fun findProducts(query: String): List<Product> {
        delay(300)
        return listOf(
            Product.basic(1, "Milk", 1.0, 2.0, 3.0, 4.0),
            Product.basic(2, "Milk2", 1.0, 2.0, 3.0, 4.0),
            Product.basic(3, "Milk3", 1.0, 2.0, 3.0, 4.0),
            Product.basic(4, "Milk4", 1.0, 2.0, 3.0, 4.0),
            Product.basic(5, "Eggs", 2.0, 3.0, 4.0, 5.0),
            Product.basic(6, "Apple", 3.0, 2.0, 0.0, 3.0),
            Product.basic(6, "Apple juice", 3.0, 2.0, 0.0, 3.0),
            Product.basic(6, "Orange juice", 3.0, 2.0, 0.0, 3.0),
            Product.basic(7, "Beaf1", 1.0, 2.0, 3.0, 1.0),
            Product.basic(8, "Beaf2", 1.0, 2.0, 3.0, 1.0),
            Product.basic(9, "Beaf3", 1.0, 2.0, 3.0, 1.0),
        ).filter { it.name.contains(query, true) }
    }

    override suspend fun allDiets(): List<Diet> {
        delay(300)
        return listOf(
            Diet(1, "Diet1"),
            Diet(2, "Diet2"),
            Diet(3, "Diet3"),
        )
    }

    override suspend fun allDishes(): List<Dish> {
        delay(300)
        return listOf(
            Dish(1, "Dish1"),
            Dish(2, "Dish2"),
            Dish(3, "Dish3"),
        )
    }
}