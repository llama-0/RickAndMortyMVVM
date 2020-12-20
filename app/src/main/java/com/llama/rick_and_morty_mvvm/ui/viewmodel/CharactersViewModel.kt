package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.Command
import com.llama.rick_and_morty_mvvm.ui.command.Command.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.view.CharactersScreenState

class CharactersViewModel(
    private val repository: RepositoryImpl,
    screenState: CharactersScreenState,
    private val resources: Resources
) :
    BaseViewModel<
            CharactersScreenState,
            Command>(screenState) {

    init {
        loadCharacters()
    }

    // пока что получается, что shouldRefreshView == true всегда
    private fun updateScreenState(
        screenState: CharactersScreenState = this.screenState,
        dataListState: List<SimpleCharacter> = screenState.dataList,
        errorLayoutVisibilityState: Boolean = screenState.errorLayoutVisibility,
        progressBarVisibilityState: Boolean = screenState.progressBarVisibility,
        isBtnRetryClicked: Boolean = screenState.isBtnRetryClicked,
        shouldRefreshView: Boolean = true
    ) {
        this.screenState = CharactersScreenState(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState,
            isBtnRetryClicked
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(
            screenState,
            screenState.dataList,
            screenState.errorLayoutVisibility,
            screenState.progressBarVisibility,
            isBtnRetryClicked = true,
            shouldRefreshView = true
        )
        Log.e(TAG, "onButtonRetryClicked: btn clicked")
    }

    fun onItemClicked(name: String) {
        executeCommand(ShowSnackbar(name))
    }

    private fun loadCharacters() {
        updateScreenState(
            screenState,
            screenState.dataList,
            errorLayoutVisibilityState = false,
            progressBarVisibilityState = true,
            shouldRefreshView = true
        ) // this is so fast in no internet connection case, that it looks like a glitch

        repository.getCharacters(object : FetchRemoteDataCallback {
            override fun onError() {
                if (screenState.isBtnRetryClicked) {
                    executeCommand(ShowSnackbar(resources.getString(R.string.check_internet_connection_message)))
                }
                updateScreenState(
                    screenState,
                    screenState.dataList,
                    errorLayoutVisibilityState = true,
                    progressBarVisibilityState = false,
                    shouldRefreshView = true
                )
            }

            override fun onSuccess(data: List<SimpleCharacter>) {
                updateScreenState(
                    screenState,
                    data,
                    errorLayoutVisibilityState = false,
                    progressBarVisibilityState = false,
                    shouldRefreshView = true
                )
            }
        })
    }

    companion object {
        private const val TAG = "TAG"
    }
}