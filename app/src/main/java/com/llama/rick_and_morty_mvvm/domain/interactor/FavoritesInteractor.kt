package com.llama.rick_and_morty_mvvm.domain.interactor

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class FavoritesInteractor {

    fun getFavorites(): List<SimpleCharacter> = emptyList()
        // todo: implement

    companion object {
        @Suppress("unused")
        private const val TAG = "CharactersInteractor"
    }
}
