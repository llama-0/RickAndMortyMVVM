package com.llama.rick_and_morty_mvvm.presentation.viewmodels

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llama.rick_and_morty_mvvm.domain.interactors.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.interactors.FavoritesInteractor
import com.llama.rick_and_morty_mvvm.presentation.mappers.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharactersScreenState
import com.llama.rick_and_morty_mvvm.presentation.screenstates.FavoritesScreenState

class CharactersViewModelFactory(
    private val interactor: CharactersInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private val screenState: CharactersScreenState,
    private val detailsScreenState: CharacterDetailsScreenState,
    private val favoritesScreenState: FavoritesScreenState,
    private val chipIdToGenderTypeMapper: ChipIdToGenderType,
    private val resources: Resources
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        CharactersViewModel::class.java -> CharactersViewModel(
            interactor,
            screenState,
            chipIdToGenderTypeMapper,
            resources
        ) as T

        CharacterDetailsViewModel::class.java -> CharacterDetailsViewModel(
            detailsScreenState,
            interactor
        ) as T

        FavoritesViewModel::class.java -> FavoritesViewModel(
            favoritesScreenState,
            favoritesInteractor
        ) as T

        else -> throw IllegalArgumentException("Unknown ViewModel class inside the factory create method")
    }
}