package com.example.dietapp.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dietapp.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}