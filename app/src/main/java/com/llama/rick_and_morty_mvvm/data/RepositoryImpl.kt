package com.llama.rick_and_morty_mvvm.data

import com.llama.rick_and_morty_mvvm.data.model.Character
import com.llama.rick_and_morty_mvvm.data.model.CharactersInfo
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.data.utils.ModelMapper
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryImpl(private val apiService: ApiService) : Repository {

    private val modelMapper = ModelMapper()

    override fun getCharacters(resource: Resource) {
        apiService.getCharactersInfo().enqueue(object : Callback<CharactersInfo> {
            override fun onFailure(call: Call<CharactersInfo>, t: Throwable) {
                resource.onError()
                // networkException (or unknown)
            }

            override fun onResponse(
                    call: Call<CharactersInfo>,
                    response: Response<CharactersInfo>
            ) {
                if (response.isSuccessful) {
                    val characterList: List<Character>? = response.body()?.characters
                    resource.onSuccess(modelMapper.mapSimpleCharacterList(characterList ?: emptyList()) {
                        modelMapper.mapSimpleCharacter(it)
                    })
                } else {
                    resource.onError()
//                    resource.onError(Throwable("response isNotSuccessful, data is null"))
                    // parse error body to determine apiError or unknownError
                }
            }
        })
    }
}