package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.llama.rick_and_morty_mvvm.databinding.FragmentFavoritesBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.FavoritesCommand
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.FavoritesScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.FavoritesViewModel

class FavoritesFragment :
    BaseFragment<FavoritesScreenState, FavoritesCommand, FavoritesViewModel>(
        FavoritesViewModel::class.java
    ) {

    private var binding: FragmentFavoritesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.let {
            initAdapter(it.rvFavorites)
        }
    }

    override fun renderView(screenState: FavoritesScreenState) {
        with(binding ?: return) {
            tvFavoritesEmpty.isVisible = screenState.isListEmpty
            setAdapter(rvFavorites, screenState.dataList)
        }
    }
}