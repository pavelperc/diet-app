package com.flamingo.dietapp.domain

data class Product(
    val id: Long,
    val name: String,
    val nutrients: Map<String, Double>
) {
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

    val calories get() = nutrients[CALORIES] ?: 0.0
    val protein get() = nutrients[PROTEIN] ?: 0.0
    val fat get() = nutrients[FAT] ?: 0.0
    val carb get() = nutrients[CARB] ?: 0.0
}

data class Dish(
    val id: Long,
    val name: String,
    val imageUrl: String,
    val ingredients: List<Ingredient>
) {
    val weight = ingredients.sumBy { it.weight }
    val calories = ingredients.sumByDouble { it.weight * it.product.calories } / 100
    val fat = ingredients.sumByDouble { it.weight * it.product.fat } / 100
    val protein = ingredients.sumByDouble { it.weight * it.product.protein } / 100
    val carb = ingredients.sumByDouble { it.weight * it.product.carb } / 100
}

data class Ingredient(
    val weight: Int,
    val product: Product
)

data class DietDay(
    val description: String,
    val dishes: List<Dish>
)

data class Diet(
    val id: Long,
    val name: String,
    val days: List<DietDay>
)