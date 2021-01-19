package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModelFactory(
    private val interactor: CharactersInteractor,
    private val screenState: CharactersScreenState,
    private val detailsScreenState: CharacterDetailsScreenState,
    private val resources: Resources
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        CharactersViewModel::class.java -> CharactersViewModel(
            interactor,
            screenState,
            resources
        ) as T

        CharacterDetailsViewModel::class.java -> CharacterDetailsViewModel(
            detailsScreenState,
            interactor
        ) as T

        else -> throw IllegalArgumentException("Unknown ViewModel class inside the factory create method")
    }
}