package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import android.os.Bundle
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

class ViewModelFactoryCreator {

    fun create(resources: Resources): CharactersViewModelFactory {
        val bundle = Bundle()
        return CharactersViewModelFactory(
            CharactersInteractorBuilder().build(),
            CharactersScreenState(),
            CharacterDetailsScreenState(null),
            resources,
            bundle
        )
    }
}