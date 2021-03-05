package com.llama.rick_and_morty_mvvm.data.network

import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("character?page=27") // it has all 4 genders. todo: remove `page` arg when migration to Paging Library will be implemented
    fun getCharactersInfo(): Single<CharactersInfo>

    @Suppress("unused")
    @GET("character/{id}")
    fun getCharacterById(@Path("id") id: Int): Single<Character>
}