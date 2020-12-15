package com.flamingo.dietapp.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Diet
import com.flamingo.dietapp.ui.diet.DietActivity
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import kotlinx.android.synthetic.main.item_diet.view.*

class DietListAdapter(val context: Context, val matchParent: Boolean) :
    RecyclerView.Adapter<DietListAdapter.ViewHolder>() {


    var diets: List<Diet> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val tvName = root.tvName
        val tvCalDish1 = root.tvCalDish1
        val tvCalDish2 = root.tvCalDish2
        val tvCalDish3 = root.tvCalDish3
        val ivDish1 = root.ivDish1
        val ivDish2 = root.ivDish2
        val ivDish3 = root.ivDish3
        val llDish1 = root.llDish1
        val llDish2 = root.llDish2
        val llDish3 = root.llDish3
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_diet, viewGroup, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.root.updateLayoutParams<ViewGroup.LayoutParams> {
            width = if (matchParent) ViewGroup.LayoutParams.MATCH_PARENT else ViewGroup.LayoutParams.WRAP_CONTENT
        }

        val diet = diets[position]
        with(viewHolder) {
            tvName.text = diet.name
            val dishes = diet.days.flatMap { it.meals.flatMap { it.dishes } }
            val dish1 = dishes.getOrNull(0)
            val dish2 = dishes.getOrNull(1)
            val dish3 = dishes.getOrNull(2)

            llDish1.isVisible = dish1 != null
            llDish2.isVisible = dish2 != null
            llDish3.isVisible = dish3 != null

            if (dish1 != null) {
                tvCalDish1.text = dish1.calories.formatCalories(false)
                Glide.with(context)
                    .load(dish1.imageUrl)
                    .into(ivDish1)
            }
            if (dish2 != null) {
                tvCalDish2.text = dish2.calories.formatCalories(false)
                Glide.with(context)
                    .load(dish2.imageUrl)
                    .into(ivDish2)
            }
            if (dish3 != null) {
                tvCalDish3.text = dish3.calories.formatCalories(false)
                Glide.with(context)
                    .load(dish3.imageUrl)
                    .into(ivDish3)
            }
            root.setOnClickListener {
                context.startActivity(Intent(context, DietActivity::class.java).apply {
                    putExtra("diet", diet)
                })
            }
        }
    }

    override fun getItemCount() = diets.size

}