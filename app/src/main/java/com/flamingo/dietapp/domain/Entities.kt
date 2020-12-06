package com.flamingo.dietapp.domain

import java.io.Serializable

interface Ingredient {
    val name: String
    val weight: Int
    val product: Product
    val calories: Double
    val fat: Double
    val protein: Double
    val carb: Double
}

data class DishIngredient(
    override val weight: Int,
    override val product: Product
) : Serializable, Ingredient {

    override val name get() = product.name
    override val calories = product.calories * weight / 100
    override val fat = product.fat * weight / 100
    override val protein = product.protein * weight / 100
    override val carb = product.carb * weight / 100
}

data class Product(
    val id: Long,
    override val name: String,
    val nutrients: Map<String, Double>
) : Serializable, Ingredient {
    override val product get() = this
    override val weight = 100

    companion object {
        fun basic(
            id: Long,
            name: String,
            calories: Double,
            protein: Double,
            fat: Double,
            carb: Double
        ) = Product(
            id, name, mapOf(CALORIES to calories, PROTEIN to protein, FAT to fat, CARB to carb)
        )

        val CALORIES = "Energy"
        val PROTEIN = "Protein"
        val FAT = "Total lipid (fat)"
        val CARB = "Carbohydrate, by difference"
    }

    override val calories = nutrients[CALORIES] ?: 0.0
    override val protein = nutrients[PROTEIN] ?: 0.0
    override val fat = nutrients[FAT] ?: 0.0
    override val carb = nutrients[CARB] ?: 0.0
}

data class Dish(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val ingredients: List<DishIngredient>
) : Serializable {
    val weight = ingredients.sumBy { it.weight }
    val calories = ingredients.sumByDouble { it.calories }
    val fat = ingredients.sumByDouble { it.fat }
    val protein = ingredients.sumByDouble { it.protein }
    val carb = ingredients.sumByDouble { it.carb }
}

data class DietDay(
    val description: String,
    val dishes: List<Dish>
)

data class Diet(
    val id: Long,
    val name: String,
    val days: List<DietDay>
)