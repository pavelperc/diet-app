package com.example.dietapp.repository

import com.example.dietapp.domain.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import ru.gildor.coroutines.okhttp.await


class Repository {
    companion object {
        const val BASE_URL = "https://dietka-app.azurewebsites.net/api"
        const val PRODUCT_LIMIT = 30
    }

    val client = OkHttpClient()

// https://dietka-app.azurewebsites.net/api/Food/FindFood?searchQuery=apple

    suspend fun findFood(query: String): List<Product> {
        val url = BASE_URL.toHttpUrl()
            .newBuilder()
            .addPathSegment("Food/FindFood")
            .addQueryParameter("searchQuery", query)
            .build()
        val request = Request.Builder()
            .url(url)
            .build()


        val response = client.newCall(request).await()
        checkResponse(response)

        return withContext(Dispatchers.IO) {
            JSONArray(response.body!!.string()).toList().take(PRODUCT_LIMIT).map { jo ->
                Product(
                    id = jo.getLong("ndbNo"),
                    name = jo.getString("longDesc"),
                    nFactor = jo.getDouble("nFactor"),
                    proFactor = jo.getDouble("proFactor"),
                    fatFactor = jo.getDouble("fatFactor"),
                    choFactor = jo.getDouble("choFactor")
                )
            }
        }
    }

    private fun JSONArray.toList() = List(length()) { i -> getJSONObject(i) }

    private fun checkResponse(response: Response) {
        if (response.code != 200) {
            throw HttpError(response.message)
        }
    }

    fun release() {
        client.connectionPool.evictAll()
    }

    class HttpError(error: String) : RuntimeException(error)
}