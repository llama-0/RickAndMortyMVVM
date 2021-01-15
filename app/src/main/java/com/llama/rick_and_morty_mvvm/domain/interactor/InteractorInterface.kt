package com.llama.rick_and_morty_mvvm.domain.interactor

import com.llama.rick_and_morty_mvvm.domain.FetchDataCallback
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

interface InteractorInterface {
    fun fetchData(callback: FetchDataCallback)
    fun getCachedData() : List<SimpleCharacter>
}