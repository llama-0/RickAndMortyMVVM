package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import android.os.Bundle
import com.llama.rick_and_morty_mvvm.data.Repository
import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

class ViewModelFactoryCreator {

    fun create(resources: Resources): CharactersViewModelFactory =
        CharactersViewModelFactory(
            CharactersInteractorBuilder().build(),
            CharactersScreenState(),
            CharacterDetailsScreenState(null),
            resources,
            Bundle()
        )
}