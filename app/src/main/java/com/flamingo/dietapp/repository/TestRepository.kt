package com.flamingo.dietapp.repository

import com.flamingo.dietapp.domain.*
import kotlinx.coroutines.delay
import kotlin.random.Random

class TestRepository : Repository {

    override suspend fun findProducts(query: String): List<Product> {
        delay(300)
        return productsBase.filter { it.name.contains(query, true) }
    }

    private val productsBase = listOf(
        Product.basic(1, "Milk", 1.0, 2.0, 3.0, 4.0),
        Product.basic(2, "Cheese", 1.0, 2.0, 3.0, 4.0),
        Product.basic(3, "Potato", 1.0, 2.0, 3.0, 4.0),
        Product.basic(4, "Onion", 1.0, 2.0, 3.0, 4.0),
        Product.basic(5, "Egg", 2.0, 3.0, 4.0, 5.0),
        Product.basic(5, "Bread", 2.2, 3.1, 6.0, 5.11),
        Product.basic(6, "Apple", 3.0, 2.0, 0.0, 3.0),
        Product.basic(7, "Apple juice", 3.0, 2.0, 0.1, 3.0),
        Product.basic(8, "Orange juice", 3.0, 2.0, 0.0, 3.0),
        Product.basic(9, "Beaf", 1.0, 2.0, 3.0, 1.0),
        Product.basic(10, "Chicken", 1.0, 4.0, 3.0, 1.0),
        Product.basic(11, "Pork", 1.0, 2.0, 3.0, 2.0),
    )

    private val randomDishes = List(15) { i -> randomDish(i) }
    private val randomDiets = List(4) { i -> randomDiet(i) }

    override suspend fun allDiets(): List<Diet> {
        delay(300)
        return randomDiets
    }

    override suspend fun allDishes(): List<Dish> {
        delay(300)
        return randomDishes
    }

    private fun randomDiet(id: Int) = Diet(
        id.toLong(),
        "Diet$id",
        List(Random.nextInt(1, 5)) { i ->
            DietDay(
                "Day${i + 1}",
                randomDishes.shuffled().take(Random.nextInt(2, 4))
            )
        }
    )

    private fun randomDish(id: Int) = Dish(
        id.toLong(),
        "Dish$id",
        assetImage(id),
        productsBase.shuffled().take(Random.nextInt(1, 5))
            .map { Ingredient(Random.nextInt(100, 1000), it) }
    )
    private fun assetImage(id: Int) = "file:///android_asset/food${(id % 6) + 1}.jpg"

}