package com.llama.rick_and_morty_mvvm.data.utils

import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/**
 * Maps data from network model (CharactersInfo) to domain model (SimpleCharacter)
 * */
class CharactersMapper {

    fun map(input: List<Character>?): List<SimpleCharacter> =
        input?.map(::mapSimpleCharacter) ?: emptyList()

    private fun mapSimpleCharacter(input: Character): SimpleCharacter =
        SimpleCharacter(
            input.id,
            input.name
        )
}