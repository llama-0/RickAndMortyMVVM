package com.llama.rick_and_morty_mvvm.data

import androidx.lifecycle.LiveData
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.data.network.Resource

interface Repository {
    fun getCharacters(): LiveData<Resource<List<SimpleCharacter>>>
}