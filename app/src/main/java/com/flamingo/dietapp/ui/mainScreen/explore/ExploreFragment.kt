package com.flamingo.dietapp.ui.mainScreen.explore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.flamingo.dietapp.R
import com.flamingo.dietapp.ui.search.DishListAdapter
import com.flamingo.dietapp.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_explore.*

class ExploreFragment : Fragment() {

    val exploreViewModel by viewModels<ExploreViewModel> {
        ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
    }

    private val dishesAdapter by lazy { DishListAdapter(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnSearch.setOnClickListener {
            startActivity(Intent(requireContext(), SearchActivity::class.java))
        }
        setupDishes()
        exploreViewModel.loadDishes()
    }


    private fun setupDishes() {
        rvDishes.adapter = dishesAdapter
        rvDishes.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rvDishes)

        exploreViewModel.dishesViewState.observe(requireActivity()) { viewState ->
            pbLoadingDishes.isVisible = viewState is ExploreViewModel.DishesViewState.Loading
            rvDishes.isVisible = viewState is ExploreViewModel.DishesViewState.Loaded
            tvDishesError.isVisible = viewState is ExploreViewModel.DishesViewState.Error
            llDishesError.isVisible = viewState is ExploreViewModel.DishesViewState.Error

            when (viewState) {
                is ExploreViewModel.DishesViewState.Loaded -> {
                    dishesAdapter.dishes = viewState.dishes
                }
                is ExploreViewModel.DishesViewState.Error -> {
                    tvDishesError.text = viewState.e.message
                }
            }
        }

        exploreViewModel.loadDishes()
    }
}