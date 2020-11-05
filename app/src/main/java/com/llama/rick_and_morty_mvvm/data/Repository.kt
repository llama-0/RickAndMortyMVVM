package com.llama.rick_and_morty_mvvm.data

import androidx.lifecycle.LiveData
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter

interface Repository {
    fun getCharacters(): LiveData<List<SimpleCharacter>>
}