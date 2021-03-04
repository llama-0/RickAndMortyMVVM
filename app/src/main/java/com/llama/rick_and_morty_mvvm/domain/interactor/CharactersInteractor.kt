package com.llama.rick_and_morty_mvvm.domain.interactor

import com.llama.rick_and_morty_mvvm.data.Repository
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import io.reactivex.rxjava3.core.Single
class CharactersInteractor(private val repository: Repository) {

    private val cachedData: ArrayList<SimpleCharacter> = arrayListOf()

    fun fetchData(): Single<List<SimpleCharacter>> {
        return repository.getCharacters()
            .doOnSuccess { cachedData.addAll(it) } // bad, try with flatMap or something
    }

    fun getCachedData(): List<SimpleCharacter> =
        cachedData

    companion object {
        @Suppress("unused")
        private const val TAG = "CharactersInteractor"
    }
}

