package com.llama.rick_and_morty_mvvm.data.interactor

import android.util.Log
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class CharactersInteractor(private val repository: RepositoryImpl) : InteractorInterface {

    private val fetchedData: ArrayList<SimpleCharacter> = arrayListOf()
    private var isError: Boolean = false

    override fun fetchData() {
        repository.getCharacters(object : FetchRemoteDataCallback {
            override fun onSuccess(data: List<SimpleCharacter>) {
                fetchedData.clear()
                fetchedData.addAll(data)
                Log.d(TAG, "onSuccess: size = ${fetchedData.size}")
            }

            override fun onError() {
                isError = true
                Log.d(TAG, "onError: isError = $isError")
            }

        })
    }

    override fun getFetchedData(): List<SimpleCharacter> =
        fetchedData

    override fun getErrorState(): Boolean =
        isError

    companion object {
        private const val TAG = "TAG"
    }
}