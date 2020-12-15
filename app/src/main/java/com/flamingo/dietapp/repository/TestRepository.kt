package com.flamingo.dietapp.repository

import com.flamingo.dietapp.domain.*
import kotlinx.coroutines.delay
import kotlin.random.Random

object TestRepository : Repository {

    override suspend fun findProducts(query: String): List<Product> {
        delay(Random.nextLong(300, 500))
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

    private val dish1 = Dish(
        1,
        "Dish 1",
        "Description",
        "Ingredients",
        "recipe",
        assetImage("food1"),
        2.0,
        3.0,
        4.0,
        5.0
    )
    private val dish2 = Dish(
        1,
        "Dish 2",
        "Description",
        "Ingredients",
        "recipe",
        assetImage("food2"),
        3.0,
        2.0,
        5.0,
        4.0
    )
    private val dish3 = Dish(
        1,
        "Dish 3",
        "Description",
        "Ingredients",
        "recipe",
        assetImage("food3"),
        1.0,
        1.0,
        3.0,
        2.0
    )
    private val testDishes = listOf(dish1, dish2, dish3)

    private val testDiets = listOf(
        Diet(
            1,
            "Diet 1",
            """The main idea is to make plant-based foods the central part of your meals. What to Eat and Drink
Vegetables (including kale, spinach, Swiss chard, collard greens, sweet potatoes, asparagus, bell peppers, and broccoli)
Fruits (such as avocado, strawberries, blueberries, watermelon, apples, grapes, bananas, grapefruit...""",
            assetImage("diet1"),
            listOf(
                DietDay(
                    "Day 1",
                    listOf(
                        DietMeal(
                            "Breakfast",
                            listOf(dish1)
                        ),
                        DietMeal(
                            "Dinner",
                            listOf(dish2, dish3)
                        )
                    )
                ),
                DietDay(
                    "Day 2",
                    listOf(
                        DietMeal(
                            "Breakfast",
                            listOf(dish1, dish2)
                        ),
                        DietMeal(
                            "Snack",
                            listOf(dish3)
                        )
                    )
                )
            )
        ),
        Diet(
            2,
            "Diet 2",
            """The main idea is to make plant-based foods the central part of your meals. What to Eat and Drink
Vegetables (including kale, spinach, Swiss chard, collard greens, sweet potatoes, asparagus, bell peppers, and broccoli)
Fruits (such as avocado, strawberries, blueberries, watermelon, apples, grapes, bananas, grapefruit...""",
            assetImage("diet2"),
            listOf(
                DietDay(
                    "Day 1",
                    listOf(
                        DietMeal(
                            "Breakfast",
                            listOf(dish1)
                        ),
                        DietMeal(
                            "Dinner",
                            listOf(dish2, dish3)
                        )
                    )
                ),
                DietDay(
                    "Day 2",
                    listOf(
                        DietMeal(
                            "Breakfast",
                            listOf(dish1, dish2)
                        ),
                        DietMeal(
                            "Snack",
                            listOf(dish3)
                        )
                    )
                )
            )
        )
    )

    fun regenerate() {
//    randomDishes = List(15) { i -> randomDish(i) }
//        randomDiets = List(5) { i -> randomDiet(i) }
    }

    override suspend fun allDiets(): List<Diet> {
        delay(Random.nextLong(300, 500))
        return testDiets
    }

    override suspend fun allDishes(): List<Dish> {
        delay(Random.nextLong(300, 500))
        return testDishes
    }

    private fun assetImage(name: String) = "file:///android_asset/$name.jpg"

}