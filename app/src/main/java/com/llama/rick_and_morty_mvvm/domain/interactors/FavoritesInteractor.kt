package com.llama.rick_and_morty_mvvm.domain.interactors

import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter

class FavoritesInteractor {

    fun getFavorites(): List<SimpleCharacter> = emptyList()
        // todo: implement

    companion object {
        @Suppress("unused")
        private const val TAG = "CharactersInteractor"
    }
}
