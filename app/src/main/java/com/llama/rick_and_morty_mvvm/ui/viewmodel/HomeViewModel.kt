package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.ui.model.ApiResponse
import com.llama.rick_and_morty_mvvm.ui.model.Character
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var liveDataList: MutableLiveData<List<Character>> = MutableLiveData()

    fun getApiResponseListObserver(): MutableLiveData<List<Character>> =
            liveDataList

    fun getCharacters() {
        val call: Call<ApiResponse> = ApiServiceBuilder.buildService().getApiResponse()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("characters() main", "Failed to execute request")
            }

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse: ApiResponse? = response.body()
                    val characters: List<Character>? = apiResponse?.characters
                    liveDataList.postValue(characters)
                } else {
                    liveDataList.postValue(null)
                }
            }
        })
    }
}