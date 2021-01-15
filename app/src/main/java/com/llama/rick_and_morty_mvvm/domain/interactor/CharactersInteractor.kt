package com.llama.rick_and_morty_mvvm.domain.interactor

import com.llama.rick_and_morty_mvvm.domain.FetchDataCallback
import com.llama.rick_and_morty_mvvm.data.Repository
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class CharactersInteractor(private val repository: Repository) : InteractorInterface {

    private val cachedData: ArrayList<SimpleCharacter> = arrayListOf()

    override fun fetchData(callback: FetchDataCallback) {
        repository.getCharacters(object : FetchDataCallback {
            override fun onSuccess(data: List<SimpleCharacter>) {
                callback.onSuccess(data)
                cachedData.clear()
                cachedData.addAll(data)
            }

            override fun onError() {
                callback.onError()
            }

        })
    }

    override fun getCachedData(): List<SimpleCharacter> =
        cachedData

    companion object {
        @Suppress("unused")
        private const val TAG = "TAG"
    }
}
