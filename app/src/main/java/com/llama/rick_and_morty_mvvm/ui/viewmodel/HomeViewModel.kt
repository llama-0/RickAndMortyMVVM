package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.Event
import com.llama.rick_and_morty_mvvm.ui.model.UIModel

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private val uiModel = UIModel()

    val errorState: LiveData<Boolean>
        get() = uiModel.errorLayoutVisibility

    val loadState: LiveData<Boolean>
        get() = uiModel.progressBarVisibility

    val dataList: LiveData<List<SimpleCharacter>>
        get() {
            loadCharacters()
            return uiModel.liveDataList
        }

    val retryBtn: LiveData<Boolean>
        get() {
            loadCharacters()
            uiModel.isRetryButtonClicked.value = true
            return uiModel.isRetryButtonClicked
        }

    val snackbarMessage: LiveData<Event<Boolean>>
        get() = uiModel.snackbarAction

    private fun loadCharacters() {
        uiModel.progressBarVisibility.value = true
        repository.getCharacters(object : Resource {
            override fun onSuccess(data: List<SimpleCharacter>) {
                setSuccessState()
                uiModel.liveDataList.value = data
            }

            override fun onError() {
                setErrorState()
            }

        })
    }

    fun setErrorState() {
        uiModel.recyclerViewVisibility.value = false
        uiModel.errorLayoutVisibility.value = true
        uiModel.progressBarVisibility.value = false
        if (uiModel.isRetryButtonClicked.value == true) {
            Log.d(TAG, "setErrorState: isBtnClicked = ${uiModel.isRetryButtonClicked.value}")
            Log.d(TAG, "setErrorState: ready to set snackbarAction to true")
            uiModel.snackbarAction.value = Event(true) // how to call only when internet is down? теперь один лишний раз
            Log.d(TAG, "setErrorState: snackbarAction = ${uiModel.snackbarAction.value!!.peekContent()}")
        } else {
            uiModel.snackbarAction.value = Event(false)
        }
    }

    fun setSuccessState() {
        uiModel.recyclerViewVisibility.value = true
        uiModel.isRetryButtonClicked.value = false
        uiModel.snackbarAction.value = Event(false)
        uiModel.progressBarVisibility.value = false
        uiModel.errorLayoutVisibility.value = false
    }

    companion object {
        private const val TAG = "TAG"
    }
}

class SnackbarMessage(val text: String)