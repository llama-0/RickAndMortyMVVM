package com.llama.rick_and_morty_mvvm.data

import com.llama.rick_and_morty_mvvm.data.mappers.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter
import io.reactivex.rxjava3.core.Single

class Repository(
    private val apiService: ApiService,
    private val charactersMapper: CharactersMapper
) {

    fun getCharacters(): Single<List<SimpleCharacter>> =
        apiService.getCharactersInfo().map { response ->
            charactersMapper.map(response.characters)
        }

    @Suppress("unused")
    companion object {
        private const val REPOSITORY_TAG = "Repository"
    }
}

