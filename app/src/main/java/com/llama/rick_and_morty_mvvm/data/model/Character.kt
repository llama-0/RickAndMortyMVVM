package com.llama.rick_and_morty_mvvm.data.model


class Character(
    val episode: List<String>, // leave it for the future
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location
)