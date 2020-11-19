package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.domain.utils.ActionLiveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val liveDataList = MutableLiveData<List<SimpleCharacter>>()
    private val recyclerViewVisibility = MutableLiveData<Boolean>() // normal view state,
                                                                    // may be redundant as errorLayout overlaps rv ...

    private val errorLayoutVisibility = MutableLiveData<Boolean>() // errorLayoutVisibility
    private val progressBarVisibility = MutableLiveData<Boolean>() // progressBarVisibility

    private val snackbarAction = MutableLiveData<Boolean>()
    private val isRetryButtonClicked = MutableLiveData<Boolean>()

    val errorState: LiveData<Boolean>
        get() = errorLayoutVisibility
    val loadState: LiveData<Boolean>
        get() = progressBarVisibility
    val dataList: LiveData<List<SimpleCharacter>>
        get() {
            loadCharacters()
            return liveDataList
        }
    val retryBtn: LiveData<Boolean>
        get() {
            loadCharacters()
            isRetryButtonClicked.value = true
            return isRetryButtonClicked
        }
    val snackbarMessage: LiveData<Boolean>
        get() = snackbarAction

    private fun loadCharacters() {
        progressBarVisibility.value = true
        repository.getCharacters(object : Resource {
            override fun onSuccess(data: List<SimpleCharacter>) {
                setSuccessState()
                liveDataList.value = data
            }

            override fun onError() {
                setErrorState()
                Log.d(TAG, "onError: loading should be set to false = ${progressBarVisibility.value}")
            }

        })
    }

    fun setErrorState() {
        recyclerViewVisibility.value = false
        errorLayoutVisibility.value = true
        progressBarVisibility.value = false
        if (isRetryButtonClicked.value == true)
            snackbarAction.value = true
    }

    fun setSuccessState() {
        recyclerViewVisibility.value = true
        progressBarVisibility.value = false
        errorLayoutVisibility.value = false
        isRetryButtonClicked.value = false
    }

    companion object {
        private const val TAG = "TAG"
    }
}

class SnackbarMessage(val text: String)