package com.flamingo.dietapp.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Dish
import com.flamingo.dietapp.ui.dish.DishViewerActivity
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import com.flamingo.dietapp.utils.NutritientFormatter.formatCarb
import com.flamingo.dietapp.utils.NutritientFormatter.formatFat
import com.flamingo.dietapp.utils.NutritientFormatter.formatProtein
import com.flamingo.dietapp.utils.NutritientFormatter.formatWeight
import kotlinx.android.synthetic.main.item_dish.view.*

class DishListAdapter(val context: Context, val matchParent: Boolean) :
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
        viewHolder.root.updateLayoutParams<ViewGroup.LayoutParams> {
            width = if (matchParent) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
        }

        val dish = dishes[position]
        viewHolder.root.setOnClickListener {
            context.startActivity(Intent(context, DishViewerActivity::class.java).apply {
                putExtra("dish", dish)
            })
        }
        viewHolder.tvName.text = dish.name
        viewHolder.tvWeight.text = dish.weight.formatWeight()
        viewHolder.tvCalories.text = dish.calories.formatCalories()
        viewHolder.tvFat.text = dish.fat.formatFat()
        viewHolder.tvProtein.text = dish.protein.formatProtein()
        viewHolder.tvCarbs.text = dish.carb.formatCarb()

        Glide.with(context)
            .load(dish.imageUrl)
            .into(viewHolder.ivImage)
    }

    override fun getItemCount() = dishes.size

}