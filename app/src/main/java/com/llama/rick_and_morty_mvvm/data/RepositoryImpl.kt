package com.llama.rick_and_morty_mvvm.data

import android.util.Log
import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(private val apiService: ApiService) : Repository {

    private val charactersMapper = CharactersMapper()

    override fun getCharacters(resource: FetchRemoteDataCallback) {
        apiService.getCharactersInfo().enqueue(object : Callback<CharactersInfo> {
            override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                Log.e(REPOSITORY_IMPL_TAG, "onFailure: getCharacters from remote failed", t)
                resource.onError()
            }

            override fun onResponse(
                call: Call<CharactersInfo>,
                response: Response<CharactersInfo>
            ) {
                if (response.isSuccessful) {
                    val characterList: List<Character>? = response.body()?.characters
                    resource.onSuccess(charactersMapper.map(characterList))
                } else {
                    Log.e(
                        REPOSITORY_IMPL_TAG,
                        "onResponse: unsuccessful response",
                        Exception(response.errorBody().toString())
                    )
                    resource.onError()
                }
            }
        })
    }

    companion object {
        private const val REPOSITORY_IMPL_TAG = "REPOSITORY_IMPL"
    }
}

