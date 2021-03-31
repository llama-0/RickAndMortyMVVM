package com.llama.rick_and_morty_mvvm.data.models


import com.google.gson.annotations.SerializedName

class CharactersInfo(
    val info: Info, // Info left for paging
    @SerializedName("results")
    val characters: List<Character>
)