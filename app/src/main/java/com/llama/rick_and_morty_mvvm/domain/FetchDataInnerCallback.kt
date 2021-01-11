package com.llama.rick_and_morty_mvvm.domain

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

interface FetchDataInnerCallback {
    fun onSuccess(data: List<SimpleCharacter>)
    fun onError()
}