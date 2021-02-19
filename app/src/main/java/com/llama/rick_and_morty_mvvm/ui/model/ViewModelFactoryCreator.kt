package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import com.llama.rick_and_morty_mvvm.domain.interactor.FavoritesInteractor
import com.llama.rick_and_morty_mvvm.ui.mapper.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.FavoritesScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

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