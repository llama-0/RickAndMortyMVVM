package com.llama.rick_and_morty_mvvm.data.model


class Character(
    val episode: List<String>, // leave it for the future
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val status: String
)