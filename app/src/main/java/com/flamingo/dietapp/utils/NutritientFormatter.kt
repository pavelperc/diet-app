package com.flamingo.dietapp.utils

object NutritientFormatter {
    fun Double.formatCalories(withText: Boolean = true) =
        if (withText) "Calories: ${toInt()}kcal"
        else "${toInt()}kcal"

    fun Double.formatProtein() = "Protein: %.1fg".format(this).replace(".", ",")
    fun Double.formatFat() = "Fat: %.1fg".format(this).replace(".", ",")
    fun Double.formatCarb() = "Carbs: %.1fg".format(this).replace(".", ",")
    fun Int.formatWeight() = "${this}g"
}