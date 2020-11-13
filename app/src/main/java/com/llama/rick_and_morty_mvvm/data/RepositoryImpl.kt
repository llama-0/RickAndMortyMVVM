package com.llama.rick_and_morty_mvvm.data

import android.util.Log
import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.data.network.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl : Repository {

    private val apiService: ApiService = ApiServiceBuilder.buildService()

    override fun getCharacters(resource: Resource) {
        apiService.getCharactersInfo().enqueue(object : Callback<CharactersInfo> {
            override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                Log.e("characters() main", "Failed to execute request, t.toString() = $t")
                resource.onError()
                // networkException (or unknown)
            }

            override fun onResponse(
                    call: Call<CharactersInfo>,
                    response: Response<CharactersInfo>
            ) {
                if (response.isSuccessful) {
                    val characterList: List<Character>? = response.body()?.characters
                    resource.onSuccess(mapSimpleCharacterList(characterList ?: emptyList()) {
                        mapSimpleCharacter(it)
                    })
                } else {
                    Log.d(this@RepositoryImpl.toString(), "onResponse: data is null")
                    resource.onError()
//                    resource.onError(Throwable("response isNotSuccessful, data is null"))
                    // parse error body to determine apiError or unknownError
                }
            }
        })
    }

    private fun mapSimpleCharacter(input: Character) : SimpleCharacter =
        SimpleCharacter(
                input.id,
                input.name
        )

    private fun mapSimpleCharacterList(input: List<Character>, mapSingle: (Character) -> SimpleCharacter): List<SimpleCharacter> =
            input.map { mapSingle(it) }
}