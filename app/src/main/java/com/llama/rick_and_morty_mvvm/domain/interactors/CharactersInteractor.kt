package com.llama.rick_and_morty_mvvm.domain.interactors

import com.llama.rick_and_morty_mvvm.data.Repository
import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter
import io.reactivex.rxjava3.core.Single

class CharactersInteractor(private val repository: Repository) {

    private val cachedData: ArrayList<SimpleCharacter> = arrayListOf()

    fun fetchData(): Single<List<SimpleCharacter>> {
        return repository.getCharacters()
            .flatMap {
                cachedData.clear()
                cachedData.addAll(it)
                Single.just(it)
            }
    }

    fun getCachedData(): List<SimpleCharacter> =
        cachedData

    companion object {
        @Suppress("unused")
        private const val TAG = "CharactersInteractor"
    }
}

