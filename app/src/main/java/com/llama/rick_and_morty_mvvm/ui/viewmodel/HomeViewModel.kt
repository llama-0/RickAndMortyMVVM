package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.domain.utils.ActionLiveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val liveDataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData()

    private val isErrorPresent: MutableLiveData<Boolean> = MutableLiveData()

    private val snackBarAction = ActionLiveData<Boolean>()

    private val isButtonClicked = MutableLiveData<Boolean>()

    // this all is no use
    fun onButtonClicked(): LiveData<Boolean> {
        if (isErrorPresent.value == true) {
            snackBarAction.sendAction(true)
        }
        return isButtonClicked
    }

    fun onButtonClickedDone() {
        isButtonClicked.value = false
        snackBarAction.value = false
    }

//    fun interceptNoInternetConnection(msg: String): LiveData<SnackbarMessage> {
//        snackBarAction.value = SnackbarMessage(msg)
//        return snackBarAction
//    }


    private fun loadCharacters() {
        repository.getCharacters(object : Resource{
            override fun onSuccess(data: List<SimpleCharacter>) {
                liveDataList.value = data
            }

            override fun onError() {
                isErrorPresent.value = true
            }

        })
    }

    fun updateErrorState(): LiveData<Boolean> {
        isErrorPresent.value = false
        return isErrorPresent
    }

    fun updateUI(): LiveData<List<SimpleCharacter>> {
        if (liveDataList.value.isNullOrEmpty()) {
            loadCharacters()
        }
        return liveDataList
    }
}

class SnackbarMessage(val text: String)
