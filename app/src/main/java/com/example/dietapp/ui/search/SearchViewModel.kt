package com.example.dietapp.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.dietapp.domain.Product
import com.example.dietapp.repository.Repository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest

sealed class SearchResult
class ValidResult(val products: List<Product>) : SearchResult()
class ErrorResult(val e: Throwable) : SearchResult()

@ExperimentalCoroutinesApi
@FlowPreview
class SearchViewModel : ViewModel() {

    companion object {
        val TAG = "SearchViewModel"
        private val MIN_QUERY_LENGTH = 0
        const val SEARCH_DELAY_MS = 500L
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    // https://www.hellsoft.se/instant-search-with-kotlin-coroutines/
    val queryChannel = BroadcastChannel<String>(Channel.CONFLATED)

    val repository by lazy { Repository() }


    private val searchResultFlow = queryChannel
        .asFlow()
        .debounce(SEARCH_DELAY_MS)
        .mapLatest {
            try {
                if (it.length < MIN_QUERY_LENGTH) {
                    return@mapLatest ValidResult(emptyList())
                }
                val searchResult = withContext(ioDispatcher) {
                    repository.findFood(it)
                }
                ValidResult(searchResult)
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

    val searchResult = searchResultFlow.asLiveData()

}