package com.llama.rick_and_morty_mvvm.data.interactor

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

interface InteractorInterface {
    fun fetchData()
    fun getFetchedData() : List<SimpleCharacter>
}