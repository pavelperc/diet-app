package com.flamingo.dietapp.ui.search

import android.os.Bundle
import android.widget.ToggleButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.flamingo.dietapp.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SearchActivity : AppCompatActivity() {

    private val productListAdapter by lazy {
        ProductListAdapter()
    }

    private val dishListAdapter by lazy {
        DishListAdapter()
    }

    private val dietListAdapter by lazy {
        DietListAdapter()
    }

    val searchViewModel by viewModels<SearchViewModel> { ViewModelProvider.AndroidViewModelFactory.getInstance(application) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        rvItems.adapter = productListAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        searchViewModel.searchProducts.observe(this) { handleSearchResult(it) }

        setupFilterButtons()

        etSearch.doAfterTextChanged { text ->
            searchViewModel.latestQuery = text.toString()
        }
    }

    private fun setupFilterButtons() {
        val filterButtons = listOf(btnDiets, btnDishes, btnProducts)
        fun select(button: ToggleButton) {
            filterButtons.forEach { it.isChecked = it == button }
        }
        btnProducts.setOnClickListener {
            select(btnProducts)
            searchViewModel.filter = SearchViewModel.Filter.Products
        }
        btnDishes.setOnClickListener {
            select(btnDishes)
            searchViewModel.filter = SearchViewModel.Filter.Dishes
        }
        btnDiets.setOnClickListener {
            select(btnDiets)
            searchViewModel.filter = SearchViewModel.Filter.Diets
        }
    }


    private fun handleSearchResult(result: SearchResult) {
        rvItems.isVisible = result.isSuccessful
        llPlaceholder.isVisible = !result.isSuccessful
        tvError.isVisible = result is ErrorResult

        when (result) {
            is ProductsResult -> {
                productListAdapter.products = result.products
                rvItems.adapter = productListAdapter
            }
            is DishesResult -> {
                dishListAdapter.dishes = result.dishes
                rvItems.adapter = dishListAdapter
            }
            is DietsResult -> {
                dietListAdapter.diets = result.diets
                rvItems.adapter = dietListAdapter
            }
            is ErrorResult -> {
                tvError.text = result.e.message ?: "Unknown error"
                tvPlaceholderText.text = getString(R.string.error_placeholder)
            }
            is EmptyResult -> {
                tvPlaceholderText.text = getString(R.string.search_placeholder)
            }
        }

    }
}