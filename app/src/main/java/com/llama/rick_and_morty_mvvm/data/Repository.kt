package com.llama.rick_and_morty_mvvm.data

import android.util.Log
import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.domain.FetchDataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(
    private val apiService: ApiService,
    private val charactersMapper: CharactersMapper
) {

    fun getCharacters(callback: FetchDataCallback) {
        apiService.getCharactersInfo().enqueue(object : Callback<CharactersInfo> {
            override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                Log.e(REPOSITORY_TAG, "onFailure: getCharacters from remote failed", t)
                callback.onError()
            }

            override fun onResponse(
                call: Call<CharactersInfo>,
                response: Response<CharactersInfo>
            ) {
                if (response.isSuccessful) {
                    val body: CharactersInfo? = response.body()
                    body?.let { }
                    if (body == null) {
                        Log.e(
                            REPOSITORY_TAG,
                            "onResponse: response is successful",
                            Exception(response.code().toString())
                        )
                        callback.onSuccess(emptyList())
                    } else {
                        callback.onSuccess(charactersMapper.map(body.characters))
                    }
                } else {
                    Log.e(
                        REPOSITORY_TAG,
                        "onResponse: unsuccessful response",
                        Exception("${response.code()}: ${response.errorBody()}")
                    )
                    callback.onError()
                }
            }
        })
    }

    companion object {
        private const val REPOSITORY_TAG = "REPOSITORY"
    }
}

