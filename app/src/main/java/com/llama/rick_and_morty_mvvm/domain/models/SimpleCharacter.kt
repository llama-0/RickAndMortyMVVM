package com.llama.rick_and_morty_mvvm.domain.models


class SimpleCharacter(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val image: String,
    val firstSeenIn: String,
    val lastSeenIn: String
)