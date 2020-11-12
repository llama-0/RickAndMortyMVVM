package com.llama.rick_and_morty_mvvm.data.model


import com.google.gson.annotations.SerializedName

class CharactersInfo(
    val info: Info,
    @SerializedName("results")
    val characters: List<Character>
)