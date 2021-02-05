package com.llama.rick_and_morty_mvvm.data

import android.util.Log
import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.domain.FetchCharacterCallback
import com.llama.rick_and_morty_mvvm.domain.FetchCharactersListCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val apiService: ApiService,
    private val charactersMapper: CharactersMapper
) {

    fun getCharacters(listCallback: FetchCharactersListCallback) {
        apiService.getCharactersInfo().enqueue(object : Callback<CharactersInfo> {
            override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                Log.e(REPOSITORY_TAG, "onFailure: getCharacters from remote failed", t)
                listCallback.onError()
            }

            override fun onResponse(
                call: Call<CharactersInfo>,
                response: Response<CharactersInfo>
            ) {
                if (response.isSuccessful) {
                    val body: CharactersInfo? = response.body()
                    if (body == null) {
                        Log.e(
                            REPOSITORY_TAG,
                            "onResponse: response is successful",
                            Exception(response.code().toString())
                        )
                    } else {
                        listCallback.onSuccess(charactersMapper.map(body.characters))
                    }
                } else {
                    Log.e(
                        REPOSITORY_TAG,
                        "onResponse: unsuccessful response",
                        Exception("${response.code()}: ${response.errorBody()}")
                    )
                    listCallback.onError()
                }
            }
        })
    }

    // copy paste inside -> todo: move to some generic method
    fun getCharacterById(id: Int, listCallback: FetchCharacterCallback) {
        apiService.getCharacterById(id).enqueue(object : Callback<Character> {
            override fun onResponse(call: Call<Character>, response: Response<Character>) {
                if (response.isSuccessful) {
                    val body: Character? = response.body()
                    if (body == null) {
                        Log.e(
                            REPOSITORY_TAG,
                            "onResponse: response is successful",
                            Exception(response.code().toString())
                        )
                    } else {
                        listCallback.onSuccess(charactersMapper.mapSimpleCharacter(body))
                    }
                } else {
                    Log.e(
                        REPOSITORY_TAG,
                        "onResponse: unsuccessful response",
                        Exception("${response.code()}: ${response.errorBody()}")
                    )
                    listCallback.onError()
                }
            }

            override fun onFailure(call: Call<Character>, t: Throwable) {
                Log.e(REPOSITORY_TAG, "onResponse: getCharacterById failed", t)
                listCallback.onError()
            }

        })
    }

    companion object {
        private const val REPOSITORY_TAG = "Repository"
    }
}

