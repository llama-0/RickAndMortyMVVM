package com.llama.rick_and_morty_mvvm.domain.interactor

import com.llama.rick_and_morty_mvvm.domain.FetchDataInnerCallback
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

interface InteractorInterface {
    fun fetchData(callback: FetchDataInnerCallback)
    fun getFetchedData() : List<SimpleCharacter>
}