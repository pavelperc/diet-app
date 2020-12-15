package com.flamingo.dietapp.ui.diet

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.DietDay
import com.flamingo.dietapp.ui.dish.DishViewerActivity
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import kotlinx.android.synthetic.main.item_diet_meal.view.*

class DietMealListAdapter(
    val context: Context,
    day: DietDay,
    val withCheckBoxes: Boolean
) : RecyclerView.Adapter<DietMealListAdapter.ViewHolder>() {

    // (breakfast, dish), (null, dish) ...
    val dishes = day.meals.flatMap { meal ->
        meal.dishes.mapIndexed { i, dish ->
            if (i == 0) meal.name to dish else null to dish
        }
    }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val tvMeal = root.tvMeal
        val divider = root.divider
        val tvName = root.tvName
        val tvCal = root.tvCal
        val chbFinished = root.chbFinished
        val ivDish = root.ivDish
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diet_meal, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (mealName, dish) = dishes[position]
        holder.tvMeal.isVisible = mealName != null
        holder.divider.isVisible = position < dishes.size - 1
        holder.tvMeal.text = mealName ?: ""
        holder.tvName.text = dish.name
        holder.tvCal.text = dish.calories.formatCalories(false)
        holder.chbFinished.isVisible = withCheckBoxes

        Glide.with(context)
            .load(dish.imageUrl)
            .into(holder.ivDish)
        holder.root.setOnClickListener {
            context.startActivity(Intent(context, DishViewerActivity::class.java).apply {
                putExtra("dish", dish)
            })
        }
        holder.chbFinished.setOnCheckedChangeListener { buttonView, isChecked ->
            holder.tvName.apply {
                if (isChecked) {
                    setTextColor(context.getColor(R.color.darker_blue))
                } else {
                    setTextColor(context.getColor(R.color.black))
                }
            }
        }
        holder.chbFinished.isChecked = false
    }

    override fun getItemCount() = dishes.size

}