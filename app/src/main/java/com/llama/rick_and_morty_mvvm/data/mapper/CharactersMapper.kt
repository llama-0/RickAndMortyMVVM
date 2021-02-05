package com.llama.rick_and_morty_mvvm.data.mapper

import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/**
 * Maps data from network model (CharactersInfo) to domain model (SimpleCharacter)
 * */
class CharactersMapper {

    fun map(input: List<Character>): List<SimpleCharacter> =
        input.map(::mapSimpleCharacter)

    fun mapSimpleCharacter(input: Character): SimpleCharacter =
        SimpleCharacter(
            input.id,
            input.name,
            input.gender,
            input.status,
            input.species,
            input.image,
            input.origin.name,
            input.location.name
        )
}