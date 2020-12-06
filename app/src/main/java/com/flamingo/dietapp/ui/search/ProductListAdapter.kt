package com.flamingo.dietapp.ui.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Ingredient
import com.flamingo.dietapp.ui.ProductViewerActivity
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import com.flamingo.dietapp.utils.NutritientFormatter.formatCarb
import com.flamingo.dietapp.utils.NutritientFormatter.formatFat
import com.flamingo.dietapp.utils.NutritientFormatter.formatProtein

class ProductListAdapter(val context: Context, val showWeight: Boolean) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var ingredients: List<Ingredient> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val tvName = root.findViewById<TextView>(R.id.tvName)
        val tvCalories = root.findViewById<TextView>(R.id.tvCalories)
        val tvProteins = root.findViewById<TextView>(R.id.tvProtein)
        val tvFats = root.findViewById<TextView>(R.id.tvFat)
        val tvCarb = root.findViewById<TextView>(R.id.tvCarbs)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)

        return ViewHolder(root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        val name = if (showWeight) {
            ingredient.name + " (${ingredient.weight}g)"
        } else {
            ingredient.name
        }
        viewHolder.tvName.text = name
        viewHolder.tvCalories.text = ingredient.calories.formatCalories()
        viewHolder.tvProteins.text = ingredient.protein.formatProtein()
        viewHolder.tvFats.text = ingredient.fat.formatFat()
        viewHolder.tvCarb.text = ingredient.carb.formatCarb()
        viewHolder.root.setOnClickListener {
            context.startActivity(Intent(context, ProductViewerActivity::class.java).apply {
                putExtra("product", ingredient.product)
            })
        }
    }

    override fun getItemCount() = ingredients.size

}