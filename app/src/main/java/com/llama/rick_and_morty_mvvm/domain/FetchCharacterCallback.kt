package com.llama.rick_and_morty_mvvm.domain

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

interface FetchCharacterCallback {
    fun onSuccess(data: SimpleCharacter)
    fun onError()
}