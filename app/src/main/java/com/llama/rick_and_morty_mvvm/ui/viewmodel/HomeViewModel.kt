package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import android.view.View // is this The reference to a View I shouldn't be including in ViewModel ??
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.domain.utils.ActionLiveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

//    private val snackbarAction = ActionLiveData<Boolean>()
//    private val isButtonClicked = MutableLiveData<Boolean>()

    private val liveDataList = MutableLiveData<List<SimpleCharacter>>()
    private val isErrorPresent = MutableLiveData<Boolean>()
    private val isLoading = MutableLiveData<Int>()

    val errorState: LiveData<Boolean>
        get() = isErrorPresent
    val loadState: LiveData<Int>
        get() = isLoading
    val dataList: LiveData<List<SimpleCharacter>>
        get() {
            loadCharacters()
            return liveDataList
        }

    private fun loadCharacters() {
        isLoading.value = View.VISIBLE
        repository.getCharacters(object : Resource {
            override fun onSuccess(data: List<SimpleCharacter>) {
                liveDataList.value = data
                isLoading.value = View.GONE
            }

            override fun onError() {
                isErrorPresent.value = true
                isLoading.value = View.GONE
                Log.d(TAG, "onError: loading should be set to GONE = ${isLoading.value}")
            }

        })
    }

//    fun updateErrorState(): LiveData<Boolean> {
//        isErrorPresent.value = false
//        return isErrorPresent
//    }

//    fun updateUI(): LiveData<List<SimpleCharacter>> {
//        if (liveDataList.value.isNullOrEmpty()) { //
//            loadCharacters()
//        }
//        return liveDataList
//    }

    companion object {
        private const val TAG = "TAG"
    }
}