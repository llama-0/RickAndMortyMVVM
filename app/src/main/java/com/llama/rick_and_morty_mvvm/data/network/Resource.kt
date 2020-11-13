package com.llama.rick_and_morty_mvvm.data.network

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

interface Resource {
    fun onSuccess(data: List<SimpleCharacter>)
    fun onError()
}