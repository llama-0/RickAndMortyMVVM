package com.llama.rick_and_morty_mvvm.ui.model

import com.llama.rick_and_morty_mvvm.data.Repository
import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
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