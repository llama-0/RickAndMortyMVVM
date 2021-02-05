package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import com.llama.rick_and_morty_mvvm.ui.mapper.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

class ViewModelFactoryCreator {

    fun create(resources: Resources): CharactersViewModelFactory =
        CharactersViewModelFactory(
            CharactersInteractorBuilder().build(),
            CharactersScreenState(),
            CharacterDetailsScreenState(null),
            ChipIdToGenderType(),
            resources
        )
}