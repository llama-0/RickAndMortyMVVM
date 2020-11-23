package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.Event
import com.llama.rick_and_morty_mvvm.ui.model.UIModel

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val uiModel = UIModel()

    val errorState: LiveData<Boolean> = uiModel.errorLayoutVisibility
    val loadState: LiveData<Boolean> = uiModel.progressBarVisibility
    val snackbarMessage: LiveData<Event<Boolean>> = uiModel.snackbarAction
    val dataList: LiveData<List<SimpleCharacter>> = uiModel.liveDataList

    val retryBtn: LiveData<Boolean>
        get() {
            loadCharacters()
            uiModel.isRetryButtonClicked.value = true
            return uiModel.isRetryButtonClicked
        }

    init {
        Log.d(TAG, "viewModel: init")
        loadCharacters()
    }

    fun getList(): List<SimpleCharacter>? = uiModel.liveDataList.value

    private fun loadCharacters() {
        uiModel.progressBarVisibility.value = true
        repository.getCharacters(object : Resource {
            override fun onError() {
                setErrorState()
            }
            override fun onSuccess(data: List<SimpleCharacter>) {
                uiModel.liveDataList.value = data
                setSuccessState()
            }
        })
    }

    fun setErrorState() {
        if (uiModel.isRetryButtonClicked.value == true) {
            Log.d(TAG, "setErrorState: ready to set snackbarAction to true")
            uiModel.snackbarAction.value = Event(true) // how to call only when internet is down? теперь один лишний раз
        }
        uiModel.errorLayoutVisibility.value = true
        uiModel.progressBarVisibility.value = false
    }

    fun setSuccessState() {
        uiModel.isRetryButtonClicked.value = false
        Log.d(TAG, "setSuccessState: retryBtn.value = ${uiModel.isRetryButtonClicked.value}")
        uiModel.snackbarAction.value = Event(false)
        uiModel.progressBarVisibility.value = false
        uiModel.errorLayoutVisibility.value = false
    }

    companion object {
        private const val TAG = "TAG"
    }
}

class SnackbarMessage(val text: String)