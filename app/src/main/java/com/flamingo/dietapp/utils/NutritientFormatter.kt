package com.flamingo.dietapp.utils

object NutritientFormatter {
    fun Double.formatCalories() = "Calories: ${toInt()}kcal"
    fun Double.formatProtein() = "Protein: %.2fg".format(this).replace(".", ",")
    fun Double.formatFat() = "Fat: %.2fg".format(this).replace(".", ",")
    fun Double.formatCarb() = "Carbs: %.2fg".format(this).replace(".", ",")
    fun Int.formatWeight() = "${this}g"
}