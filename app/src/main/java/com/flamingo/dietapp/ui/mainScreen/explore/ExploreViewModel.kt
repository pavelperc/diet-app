package com.flamingo.dietapp.ui.mainScreen.explore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.flamingo.dietapp.Preferences
import com.flamingo.dietapp.domain.Diet
import com.flamingo.dietapp.domain.Dish
import com.flamingo.dietapp.repository.RealRepository
import com.flamingo.dietapp.repository.Repository
import com.flamingo.dietapp.repository.TestRepository
import kotlinx.coroutines.launch

class ExploreViewModel(application: Application) : AndroidViewModel(application) {
    sealed class DishesViewState {
        object Loading : DishesViewState()
        class Error(val e: Throwable) : DishesViewState()
        class Loaded(val dishes: List<Dish>) : DishesViewState()
    }

    sealed class DietsViewState {
        object Loading : DietsViewState()
        class Error(val e: Throwable) : DietsViewState()
        class Loaded(val diets: List<Diet>) : DietsViewState()
    }

    val preferences by lazy { Preferences(application.applicationContext) }

    private val _testRepository = TestRepository
    private val _realRepository = RealRepository()

    val repository: Repository
        get() = if (preferences.useTestRepository) _testRepository else _realRepository

    val dishesViewState = MutableLiveData<DishesViewState>(DishesViewState.Loading)
    val dietsViewState = MutableLiveData<DietsViewState>(DietsViewState.Loading)

    fun loadDishes() {
        dishesViewState.value = DishesViewState.Loading
        viewModelScope.launch {
            try {
                val dishes = repository.allDishes()
                dishesViewState.postValue(DishesViewState.Loaded(dishes))
            } catch (e: Throwable) {
                dishesViewState.postValue(DishesViewState.Error(e))
            }
        }
    }
    fun loadDiets() {
        dietsViewState.value = DietsViewState.Loading
        viewModelScope.launch {
            try {
                val diets = repository.allDiets()
                dietsViewState.postValue(DietsViewState.Loaded(diets))
            } catch (e: Throwable) {
                dietsViewState.postValue(DietsViewState.Error(e))
            }
        }
    }

}