package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.ui.view.HomeFragment

class HomeViewModel(repository: RepositoryImpl) : ViewModel() {

    private val liveDataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData()

    fun getLiveDataList(): LiveData<List<SimpleCharacter>> {
        RepositoryImpl().getCharacters(object : Resource{ // repository which has been passed to HomeViewModel constructor is unreachable here (why?)
            override fun onSuccess(data: List<SimpleCharacter>) {
                liveDataList.value = data
            }

            override fun onError(t: Throwable) {
                liveDataList.value = emptyList() // remove throwable from onError() args or find out how to handle it
            }

        })
        return liveDataList
    }
}