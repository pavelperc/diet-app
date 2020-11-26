package com.example.dietapp.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dietapp.R
import com.example.dietapp.domain.Product

class ProductListAdapter() :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {


    var products: List<Product> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        val tvName = root.findViewById<TextView>(R.id.tvName)
        val tvNFactor = root.findViewById<TextView>(R.id.tvNFactor)
        val tvProFactor = root.findViewById<TextView>(R.id.tvProFactor)
        val tvFatFactor = root.findViewById<TextView>(R.id.tvFatFactor)
        val tvChoFactor = root.findViewById<TextView>(R.id.tvChoFactor)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)

        return ViewHolder(root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val product = products[position]
        viewHolder.tvName.text = product.name
        viewHolder.tvNFactor.text = product.nFactor.toString().replace(".", ",")
        viewHolder.tvProFactor.text = product.proFactor.toString().replace(".", ",")
        viewHolder.tvFatFactor.text = product.fatFactor.toString().replace(".", ",")
        viewHolder.tvChoFactor.text = product.choFactor.toString().replace(".", ",")
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = products.size

}