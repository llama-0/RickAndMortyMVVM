package com.llama.rick_and_morty_mvvm.presentation.screenstates

import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter
import com.llama.rick_and_morty_mvvm.presentation.base.BaseScreenState

class CharacterDetailsScreenState(
    val character: SimpleCharacter? = null
) : BaseScreenState {
    val id: Int? = character?.id
    val name: String? = character?.name
    val gender: String? = character?.gender
    val status: String? = character?.status
    val species: String? = character?.species
    val image: String? = character?.image
    val firstSeenIn: String? = character?.firstSeenIn
    val lastSeenIn: String? = character?.lastSeenIn
}