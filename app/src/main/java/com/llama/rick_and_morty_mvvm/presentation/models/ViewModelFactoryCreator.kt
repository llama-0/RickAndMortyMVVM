package com.llama.rick_and_morty_mvvm.presentation.models

import android.content.res.Resources
import com.llama.rick_and_morty_mvvm.domain.interactors.FavoritesInteractor
import com.llama.rick_and_morty_mvvm.presentation.mappers.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharactersScreenState
import com.llama.rick_and_morty_mvvm.presentation.screenstates.FavoritesScreenState
import com.llama.rick_and_morty_mvvm.presentation.viewmodels.CharactersViewModelFactory

class ViewModelFactoryCreator {

    fun create(resources: Resources): CharactersViewModelFactory =
        CharactersViewModelFactory(
            CharactersInteractorBuilder().build(),
            FavoritesInteractor(),
            CharactersScreenState(),
            CharacterDetailsScreenState(null),
            FavoritesScreenState(),
            ChipIdToGenderType(),
            resources
        )
}