package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.ui.model.GenderTypes
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModelFactory(
    private val sharedPrefs: SharedPreferences,
    private val interactor: CharactersInteractor,
    private val screenState: CharactersScreenState,
    private val detailsScreenState: CharacterDetailsScreenState,
    private val resources: Resources,
    private val genderTypes: GenderTypes
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        CharactersViewModel::class.java -> CharactersViewModel(
            sharedPrefs,
            interactor,
            screenState,
            resources,
            genderTypes
        ) as T

        CharacterDetailsViewModel::class.java -> CharacterDetailsViewModel(
            detailsScreenState,
            sharedPrefs,
            interactor
        ) as T

        else -> throw IllegalArgumentException("Unknown ViewModel class inside the factory create method")
    }
}