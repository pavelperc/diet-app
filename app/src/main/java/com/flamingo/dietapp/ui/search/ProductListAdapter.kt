package com.flamingo.dietapp.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Product

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
        val tvProteins = root.findViewById<TextView>(R.id.tvProteins)
        val tvFats = root.findViewById<TextView>(R.id.tvFats)
        val tvCarb = root.findViewById<TextView>(R.id.tvCarb)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)

        return ViewHolder(root)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product = products[position]
        viewHolder.tvName.text = product.name
        viewHolder.tvCalories.text = "%.3f".format(product.calories).replace(".", ",")
        viewHolder.tvProteins.text = "%.3f".format(product.protein).replace(".", ",")
        viewHolder.tvFats.text = "%.3f".format(product.fat).replace(".", ",")
        viewHolder.tvCarb.text = "%.3f".format(product.carb).replace(".", ",")
    }

    override fun getItemCount() = products.size

}