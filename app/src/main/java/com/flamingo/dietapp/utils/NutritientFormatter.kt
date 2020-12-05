package com.flamingo.dietapp.utils

object NutritientFormatter {
    fun Double.formatCalories() = "${toInt()}kcal"
    fun Double.formatProtein() = "%.2fg".format(this).replace(".", ",")
    fun Double.formatFat() = "%.2fg".format(this).replace(".", ",")
    fun Double.formatCarb() = "%.2fg".format(this).replace(".", ",")
}