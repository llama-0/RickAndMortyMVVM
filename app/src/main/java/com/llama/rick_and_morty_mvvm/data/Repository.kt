package com.llama.rick_and_morty_mvvm.data

import androidx.lifecycle.LiveData
import com.llama.rick_and_morty_mvvm.data.model.Character

interface Repository {
    fun getCharacters(): LiveData<List<Character>>
}