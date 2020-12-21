package com.llama.rick_and_morty_mvvm.domain.model

class FilterByGender(
    private val characters: List<SimpleCharacter>,
    private val gender: String
) {
    fun filterByGender(): List<SimpleCharacter> =
        characters.filter { it.gender == gender }
}