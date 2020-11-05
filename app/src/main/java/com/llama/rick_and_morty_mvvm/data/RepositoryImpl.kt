package com.llama.rick_and_morty_mvvm.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl : Repository {

    private val apiService: ApiService = ApiServiceBuilder.buildService()

    override fun getCharacters(): LiveData<List<SimpleCharacter>> {
        val data = MutableLiveData<List<SimpleCharacter>>()
        apiService.getCharactersInfo().enqueue(object : Callback<CharactersInfo> {
            override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                Log.e("characters() main", "Failed to execute request")
            }

            override fun onResponse(
                    call: Call<CharactersInfo>,
                    response: Response<CharactersInfo>
            ) {
                if (response.isSuccessful) {
                    val characterList: List<Character>? = response.body()?.characters
                    if (characterList != null) {
                        data.value = mapSimpleCharacterList(characterList) {
                            mapSimpleCharacter(it)
                        }
                    } else {
                        Log.d(this@RepositoryImpl.toString(), "onResponse: received empty character list")
                    }
                } else {
                    Log.d(this@RepositoryImpl.toString(), "onResponse: data is null")
                    data.value = null
                }
            }
        })
        return data
    }

    private fun mapSimpleCharacter(input: Character) : SimpleCharacter =
        SimpleCharacter(
                input.id,
                input.name
        )

    private fun mapSimpleCharacterList(input: List<Character>, mapSingle: (Character) -> SimpleCharacter): List<SimpleCharacter> =
            input.map { mapSingle(it) }
}