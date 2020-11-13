package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.data.network.Resource

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val liveDataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData()

    fun getLiveDataList(): LiveData<List<SimpleCharacter>> {
        repository.getCharacters(object : Resource{
            override fun onSuccess(data: List<SimpleCharacter>) {
                liveDataList.value = data
            }

            override fun onError() {
                liveDataList.value = emptyList()
            }

        })
        return liveDataList
    }
}