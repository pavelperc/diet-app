package com.flamingo.dietapp.ui.dish

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Dish
import com.flamingo.dietapp.ui.search.ProductListAdapter
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import com.flamingo.dietapp.utils.NutritientFormatter.formatCarb
import com.flamingo.dietapp.utils.NutritientFormatter.formatFat
import com.flamingo.dietapp.utils.NutritientFormatter.formatProtein
import com.flamingo.dietapp.utils.NutritientFormatter.formatWeight
import kotlinx.android.synthetic.main.activity_dish_viewer.*

class DishViewerActivity : AppCompatActivity() {

    val dish by lazy { intent.getSerializableExtra("dish") as Dish }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_viewer)

        btnBack.setOnClickListener {
            onBackPressed()
        }
        tvName.text = dish.name
        tvCalories.text = dish.calories.formatCalories()
        tvFat.text = dish.fat.formatFat()
        tvProtein.text = dish.protein.formatProtein()
        tvCarbs.text = dish.carb.formatCarb()

        Glide.with(this)
            .load(dish.imageUrl)
            .into(ivImage)

        tvDescription.text = dish.description
        tvRecipe.text = dish.recipe
        tvIngredients.text = dish.ingredients

        tvDescription.isVisible = dish.description != null
        tvDescriptionHeader.isVisible = dish.description != null
        tvRecipe.isVisible = dish.recipe != null
        tvRecipeHeader.isVisible = dish.recipe != null
        tvIngredients.isVisible = dish.ingredients != null
        tvIngredientsHeader.isVisible = dish.ingredients != null
    }
}