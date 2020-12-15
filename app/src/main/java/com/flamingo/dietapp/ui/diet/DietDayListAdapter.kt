package com.flamingo.dietapp.ui.diet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Diet
import kotlinx.android.synthetic.main.item_diet_day.view.*

class DietDayListAdapter(
    val context: Context,
    val diet: Diet,
    val withCheckBoxes: Boolean
) : RecyclerView.Adapter<DietDayListAdapter.ViewHolder>() {

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val tvName = root.tvName
        val rvDietMeals = root.rvDietMeals
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_diet_day, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = diet.days[position]
        holder.tvName.text = day.name
        holder.rvDietMeals.adapter = DietMealListAdapter(context, day, withCheckBoxes)
        holder.rvDietMeals.layoutManager = LinearLayoutManager(context)
    }

    override fun getItemCount() = diet.days.size

}