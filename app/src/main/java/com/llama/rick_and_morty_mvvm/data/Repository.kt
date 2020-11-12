package com.llama.rick_and_morty_mvvm.data

import com.llama.rick_and_morty_mvvm.data.network.Resource

interface Repository {
    fun getCharacters(resource: Resource)
}