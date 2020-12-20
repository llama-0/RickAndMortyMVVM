package com.llama.rick_and_morty_mvvm.data

import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback

interface Repository {
    fun getCharacters(resource: FetchRemoteDataCallback)
}