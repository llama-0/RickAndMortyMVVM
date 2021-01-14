package com.llama.rick_and_morty_mvvm.domain.interactor

import com.llama.rick_and_morty_mvvm.domain.FetchDataInnerCallback
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class CharactersInteractor(private val repository: RepositoryImpl) : InteractorInterface {

    private val cachedData: ArrayList<SimpleCharacter> = arrayListOf()

    override fun fetchData(callback: FetchDataInnerCallback) {
        repository.getCharacters(object : FetchRemoteDataCallback {
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
