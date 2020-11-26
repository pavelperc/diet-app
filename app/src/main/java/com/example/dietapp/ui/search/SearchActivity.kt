package com.example.dietapp.ui.search

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dietapp.R
import com.example.dietapp.utils.commonUtils.toast
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class SearchActivity : AppCompatActivity() {

    private val productListAdapter by lazy {
        ProductListAdapter()
    }

    val searchViewModel by viewModels<SearchViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        rvProducts.adapter = productListAdapter
        rvProducts.layoutManager = LinearLayoutManager(this)

        searchViewModel.searchResult.observe(this) { handleSearchResult(it) }

        etSearch.doAfterTextChanged { text ->
            lifecycleScope.launch {
                searchViewModel.queryChannel.send(text.toString())
            }
        }
    }


    private fun handleSearchResult(result: SearchResult) {
        when (result) {
            is ValidResult -> {
                productListAdapter.products = result.products
            }
            is ErrorResult -> {
                productListAdapter.products = emptyList()
                toast(result.e.message ?: "Some error")
            }
        }

    }
}