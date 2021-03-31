package com.llama.rick_and_morty_mvvm.presentation.models

import com.llama.rick_and_morty_mvvm.data.Repository
import com.llama.rick_and_morty_mvvm.data.mappers.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder

class RepositoryBuilder {

    fun build(): Repository =
        Repository(
            ApiServiceBuilder(STR_BASE_URL).buildService(),
            CharactersMapper()
        )

    companion object {
        private const val STR_BASE_URL = "https://rickandmortyapi.com/api/"
    }
}