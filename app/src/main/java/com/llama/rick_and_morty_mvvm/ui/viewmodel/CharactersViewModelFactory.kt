package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.SharedPreferences
import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModelFactory(
    private val repository: RepositoryImpl,
    private val sharedPrefs: SharedPreferences,
    private val interactor: CharactersInteractor,
//    private val list: List<SimpleCharacter>, // I can use list of data in details fragment, not interactor
    private val screenState: CharactersScreenState,
    private val detailsScreenState: CharacterDetailsScreenState,
    private val resources: Resources
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        CharactersViewModel::class.java -> CharactersViewModel(
            repository,
            sharedPrefs,
            interactor,
            screenState,
            resources
        ) as T

        CharacterDetailsViewModel::class.java -> CharacterDetailsViewModel(
            detailsScreenState,
            sharedPrefs,
            interactor
        ) as T

        else -> throw IllegalArgumentException("Unknown ViewModel class inside the factory create method")
    }
}