package com.flamingo.dietapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Dish

class DishListAdapter() :
    RecyclerView.Adapter<DishListAdapter.ViewHolder>() {


    var dishes: List<Dish> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val tvName = root.findViewById<TextView>(R.id.tvName)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_dish, viewGroup, false)

        return ViewHolder(root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val dish = dishes[position]
        viewHolder.tvName.text = dish.name
    }

    override fun getItemCount() = dishes.size

}