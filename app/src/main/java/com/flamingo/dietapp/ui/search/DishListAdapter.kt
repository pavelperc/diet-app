package com.flamingo.dietapp.ui.search

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Dish
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import com.flamingo.dietapp.utils.NutritientFormatter.formatCarb
import com.flamingo.dietapp.utils.NutritientFormatter.formatFat
import com.flamingo.dietapp.utils.NutritientFormatter.formatProtein
import kotlinx.android.synthetic.main.item_dish.view.*

class DishListAdapter(val context: Context) :
    RecyclerView.Adapter<DishListAdapter.ViewHolder>() {


    var dishes: List<Dish> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val tvName = root.tvName
        val tvWeight = root.tvWeight
        val ivImage = root.ivImage
        val tvCalories = root.tvCalories
        val tvFat = root.tvFat
        val tvProtein = root.tvProtein
        val tvCarbs = root.tvCarbs
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_dish, viewGroup, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dish = dishes[position]
        viewHolder.tvName.text = dish.name
        viewHolder.tvWeight.text = "${dish.weight}g"
        viewHolder.tvCalories.text = "Calories: " + dish.calories.formatCalories()
        viewHolder.tvFat.text = "Fat: " + dish.fat.formatFat()
        viewHolder.tvProtein.text = "Protein: " + dish.protein.formatProtein()
        viewHolder.tvCarbs.text = "Carbs: " + dish.carb.formatCarb()

        Glide.with(context)
            .load(Uri.parse(dish.imageUrl))
            .into(viewHolder.ivImage)
    }

    override fun getItemCount() = dishes.size

}