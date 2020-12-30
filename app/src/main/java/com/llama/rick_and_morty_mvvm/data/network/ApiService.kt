package com.llama.rick_and_morty_mvvm.data.network

import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("character?page=27") // it has all 4 genders. todo: remove `page` arg when migration to Paging Library will be implemented
    fun getCharactersInfo(): Call<CharactersInfo>
}