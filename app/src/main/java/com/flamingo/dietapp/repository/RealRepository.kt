package com.flamingo.dietapp.repository

import com.flamingo.dietapp.domain.*
import com.flamingo.dietapp.utils.commonUtils.nullableString
import com.flamingo.dietapp.utils.commonUtils.toList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
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
                        .filterKeys {
                            !(it.getOrNull(0)?.isDigit() ?: true)
                        } // remove strange digits
                )
            }
        }
    }

    override suspend fun allDishes(): List<Dish> {
        val url = BASE_URL.toHttpUrl()
            .newBuilder()
            .addPathSegment("Food/GetPreparedDishes")
            .build()
        val request = Request.Builder()
            .url(url)
            .build()


        val response = client.newCall(request).await()
        checkResponse(response)

        return withContext(Dispatchers.IO) {
            JSONArray(response.body!!.string()).toList().map { jo ->
                parseDish(jo)
            }
        }
    }

    override suspend fun allDiets(): List<Diet> {
        val url = BASE_URL.toHttpUrl()
            .newBuilder()
            .addPathSegment("Food/GetWholeDiets")
            .build()
        val request = Request.Builder()
            .url(url)
            .build()


        val response = client.newCall(request).await()
        checkResponse(response)

        return withContext(Dispatchers.IO) {
            JSONArray(response.body!!.string()).toList().map { joDiet ->
                Diet(
                    name = joDiet.getString("dietName"),
                    previewImage = joDiet.getString("preview"),
                    id = 0, // fixme need real id
                    description = joDiet.optString("dietDescription"),
                    days = joDiet.getJSONArray("dietPlan").toList()
                        .groupBy {
                            it.get("dayNumber")
                        }.mapValues { it.value.groupBy { it.getString("meal") } }
                        .map { (dayNumber, mealsByName) ->
                            DietDay(
                                name = "Day $dayNumber",
                                meals = mealsByName.map { (mealName, dishes) ->
                                    DietMeal(
                                        name = mealName,
                                        dishes = dishes.mapNotNull { joDay ->
                                            if (!joDay.isNull("dish"))
                                                parseDish(joDay.getJSONObject("dish"))
                                            else null
                                        }
                                    )
                                }
                            )
                        }
                )
            }
        }
    }

    private fun parseDish(jo: JSONObject) = Dish(
        id = jo.getLong("dishId"),
        name = jo.getString("dishName"),
        description = jo.nullableString("dishDescription"),
        ingredients = jo.nullableString("ingredientList"),
        recipe = jo.nullableString("directions"),
        calories = jo.getInt("kcal").toDouble(),
        protein = jo.getInt("protein").toDouble(),
        fat = jo.getInt("fat").toDouble(),
        carb = jo.getInt("carbs").toDouble(),
        imageUrl = jo.getString("picsUrl")
    )


    private fun checkResponse(response: Response) {
        if (response.code != 200) {
            throw Repository.HttpError(response.message)
        }
    }
}