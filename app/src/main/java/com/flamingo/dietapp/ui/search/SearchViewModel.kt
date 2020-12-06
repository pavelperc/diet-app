package com.flamingo.dietapp.ui.search

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.flamingo.dietapp.Preferences
import com.flamingo.dietapp.domain.Diet
import com.flamingo.dietapp.domain.Dish
import com.flamingo.dietapp.domain.Product
import com.flamingo.dietapp.repository.RealRepository
import com.flamingo.dietapp.repository.Repository
import com.flamingo.dietapp.repository.TestRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

sealed class SearchResult(val isSuccessful: Boolean)
class ProductsResult(val products: List<Product>) : SearchResult(true)
class DietsResult(val diets: List<Diet>) : SearchResult(true)
class DishesResult(val dishes: List<Dish>) : SearchResult(true)
object EmptyResult : SearchResult(false)
class ErrorResult(val e: Throwable) : SearchResult(false)

@ExperimentalCoroutinesApi
@FlowPreview
class SearchViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        val TAG = "SearchViewModel"
        const val SEARCH_DELAY_MS = 300L
    }

    // https://www.hellsoft.se/instant-search-with-kotlin-coroutines/
    private val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    var latestQuery = ""
        set(value) {
            field = value
            viewModelScope.launch {
                queryChannel.send(value)
            }
        }

    val preferences by lazy { Preferences(application.applicationContext) }

    val repository: Repository by lazy {
        if (preferences.useTestRepository) TestRepository() else RealRepository()
    }


    enum class Filter { Products, Dishes, Diets }

    var filter: Filter = Filter.Products
        set(value) {
            field = value
            viewModelScope.launch {
                queryChannel.send(latestQuery)
            }
        }

    private val searchProductsFlow = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MS)
        .mapLatest { query ->
            try {
                if (query.isEmpty()) {
                    return@mapLatest EmptyResult
                }
                when (filter) {
                    Filter.Products -> {
                        val result = repository.findProducts(query)
                        if (result.isEmpty()) EmptyResult
                        else ProductsResult(result)
                    }
                    Filter.Dishes -> {
                        val result = repository.findDishes(query)
                        if (result.isEmpty()) EmptyResult
                        else DishesResult(result)
                    }
                    Filter.Diets -> {
                        val result = repository.findDiets(query)
                        if (result.isEmpty()) EmptyResult
                        else DietsResult(result)
                    }
                }
            } catch (e: Throwable) {
                if (e is CancellationException) {
                    println("Search was cancelled!")
                    throw e
                } else {
                    Log.e(TAG, "search error", e)
                    ErrorResult(e)
                }
            }
        }

    val searchProducts = searchProductsFlow.asLiveData()
}