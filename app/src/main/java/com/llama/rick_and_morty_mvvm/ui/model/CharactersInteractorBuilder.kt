package com.llama.rick_and_morty_mvvm.ui.model

import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor

class CharactersInteractorBuilder {

    fun build(): CharactersInteractor =
        CharactersInteractor(RepositoryBuilder().build())
}