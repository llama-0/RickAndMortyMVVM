package com.llama.rick_and_morty_mvvm.data.models


class Character(
    val id: Int,
    val name: String,
    val gender: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location
)