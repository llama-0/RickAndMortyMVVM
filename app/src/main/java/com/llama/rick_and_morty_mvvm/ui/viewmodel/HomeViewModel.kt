package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.data.network.Resource

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val liveDataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData()

    // todo: 1. make fun getLiveDataList(): LiveData<List<SimpleCharacter>>  void
    //  2. and then rename method.

    private val eventLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val errorState: LiveData<Boolean>
        get() = eventLiveData

    private fun setLiveDataList() {
        repository.getCharacters(object : Resource{
            override fun onSuccess(data: List<SimpleCharacter>) {
                liveDataList.value = data
            }

            override fun onError() {
                liveDataList.value = emptyList()
            }

        })
    }

    fun updateUI(): LiveData<List<SimpleCharacter>> {
        if (liveDataList.value.isNullOrEmpty()) {
//            onShowError()
            setLiveDataList()
//            onHideError()
        }
        return liveDataList
    }

    /** Method for managing error state **/

    fun onShowError() {
        eventLiveData.value = true
    }

    fun onHideError() {
        eventLiveData.value = false
    }

}