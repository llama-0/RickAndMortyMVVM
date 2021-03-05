package com.llama.rick_and_morty_mvvm.data

import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import io.reactivex.rxjava3.core.Single

class Repository(
    private val apiService: ApiService,
    private val charactersMapper: CharactersMapper
) {

    fun getCharacters(): Single<List<SimpleCharacter>> =
        apiService.getCharactersInfo().map {
            charactersMapper.map(it.characters)
        }

    @Suppress("unused")
    companion object {
        private const val REPOSITORY_TAG = "Repository"
    }
}

