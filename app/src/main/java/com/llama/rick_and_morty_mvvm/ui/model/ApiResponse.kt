package com.llama.rick_and_morty_mvvm.ui.model


import com.google.gson.annotations.SerializedName

data class ApiResponse(
        @SerializedName("info")
    val info: Info,
        @SerializedName("results")
    val characters: List<Character>
)