package com.llama.rick_and_morty_mvvm.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentFavoritesBinding
import com.llama.rick_and_morty_mvvm.presentation.base.BaseFragment
import com.llama.rick_and_morty_mvvm.presentation.commands.FavoritesCommand
import com.llama.rick_and_morty_mvvm.presentation.screenstates.FavoritesScreenState
import com.llama.rick_and_morty_mvvm.presentation.viewmodels.FavoritesViewModel

class FavoritesFragment :
    BaseFragment<FavoritesScreenState, FavoritesCommand, FavoritesViewModel>(
        R.layout.fragment_favorites,
        FavoritesViewModel::class.java
    ) {

    private val binding: FragmentFavoritesBinding by viewBinding(FragmentFavoritesBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter(binding.rvFavorites)
    }

    override fun renderView(screenState: FavoritesScreenState) {
        with(binding) {
            tvFavoritesEmpty.isVisible = screenState.isListEmpty
            setAdapter(rvFavorites, screenState.dataList)
        }
    }
}