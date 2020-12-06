package com.flamingo.dietapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flamingo.dietapp.R
import com.flamingo.dietapp.domain.Product
import kotlinx.android.synthetic.main.activity_product_viewer.*

class ProductViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_viewer)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val product = intent.getSerializableExtra("product") as Product

        tvName.text = product.name
        tvNutrients.text = product.nutrients.toSortedMap { s1, s2 ->
            s1.compareTo(s2)
        }.toList().joinToString("\n") { (nutr, value) -> "${nutr}: $value" }

    }
}