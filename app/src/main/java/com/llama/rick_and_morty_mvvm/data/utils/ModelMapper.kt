package com.llama.rick_and_morty_mvvm.data.utils

import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/*
* Maps data from network model (CharactersInfo) to domain model (SimpleCharacter)
* */
class ModelMapper {

    fun mapSimpleCharacter(input: Character) : SimpleCharacter =
        SimpleCharacter(
            input.id,
            input.name
        )

    fun mapSimpleCharacterList(input: List<Character>, mapSingle: (Character) -> SimpleCharacter): List<SimpleCharacter> =
        input.map { mapSingle(it) }
}