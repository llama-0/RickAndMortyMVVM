package com.llama.rick_and_morty_mvvm.presentation.models

import com.llama.rick_and_morty_mvvm.domain.interactors.CharactersInteractor

class CharactersInteractorBuilder {

    fun build(): CharactersInteractor =
        CharactersInteractor(RepositoryBuilder().build())
}