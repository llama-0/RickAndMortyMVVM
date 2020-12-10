package com.llama.rick_and_morty_mvvm.data.network

import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("character")
    fun getCharactersInfo(): Call<CharactersInfo>
}