package com.flamingo.dietapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Product
import com.flamingo.dietapp.utils.NutritientFormatter.formatCalories
import com.flamingo.dietapp.utils.NutritientFormatter.formatCarb
import com.flamingo.dietapp.utils.NutritientFormatter.formatFat
import com.flamingo.dietapp.utils.NutritientFormatter.formatProtein

class ProductListAdapter() :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {


    var products: List<Product> = emptyList()
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
        val product = products[position]
        viewHolder.tvName.text = product.name
        viewHolder.tvCalories.text = product.calories.formatCalories()
        viewHolder.tvProteins.text = product.protein.formatProtein()
        viewHolder.tvFats.text = product.fat.formatFat()
        viewHolder.tvCarb.text = product.carb.formatCarb()
    }

    override fun getItemCount() = products.size

}