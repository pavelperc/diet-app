package com.flamingo.dietapp.repository

import com.flamingo.dietapp.domain.Diet
import com.flamingo.dietapp.domain.Dish
import com.flamingo.dietapp.domain.Product
import com.flamingo.dietapp.utils.commonUtils.toList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import ru.gildor.coroutines.okhttp.await


class RealRepository : Repository {
    companion object {
        const val BASE_URL = "https://dietka-app.azurewebsites.net/api"
        const val PRODUCT_LIMIT = 30
    }

    val client = OkHttpClient()

// https://dietka-app.azurewebsites.net/api/Food/FindFood?searchQuery=apple

    override suspend fun findProducts(query: String): List<Product> {
        val url = BASE_URL.toHttpUrl()
            .newBuilder()
            .addPathSegment("Food/FindFood")
            .addQueryParameter("searchQuery", query)
            .addQueryParameter("page", "1")
            .build()
        val request = Request.Builder()
            .url(url)
            .build()


        val response = client.newCall(request).await()
        checkResponse(response)

        return withContext(Dispatchers.IO) {
            JSONArray(response.body!!.string()).toList().take(PRODUCT_LIMIT).map { jo ->
                val foodDes = jo.getJSONObject("foodDes")
                Product(
                    id = foodDes.getLong("ndbNo"),
                    name = foodDes.getString("longDesc"),
                    nutrients = jo.getJSONArray("nutrients").toList()
                        .reversed() // reverse to get the first if it has duplicates
                        .associate { it.getString("nutrientName") to it.getDouble("nutrientValue") }
                )
            }
        }
    }

    override suspend fun allDishes(): List<Dish> {
        TODO("Not yet implemented")
    }

    override suspend fun allDiets(): List<Diet> {
        TODO("Not yet implemented")
    }


    private fun checkResponse(response: Response) {
        if (response.code != 200) {
            throw Repository.HttpError(response.message)
        }
    }
}