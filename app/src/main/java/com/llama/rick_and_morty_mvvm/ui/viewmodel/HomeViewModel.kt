package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.utils.Command
import com.llama.rick_and_morty_mvvm.ui.utils.HomeScreenState
import com.llama.rick_and_morty_mvvm.ui.utils.ShowSnackbar

class HomeViewModel(
    private val repository: RepositoryImpl,
    model: HomeScreenState
) :
    BaseViewModel<
            HomeScreenState,
            Command>(model) {

    init {
        loadCharacters("")
    }

    // пока что получается, что shouldRefreshView == true всегда
    private fun updateScreenState(
        screenState: HomeScreenState = this.model,
        dataListState: List<SimpleCharacter> = screenState.dataList,
        errorLayoutVisibilityState: Boolean = screenState.errorLayoutVisibility,
        progressBarVisibilityState: Boolean = screenState.progressBarVisibility,
        shouldRefreshView: Boolean = true
    ) {
        this.model = HomeScreenState(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    fun onButtonRetryClicked(msg: String) {
        loadCharacters(msg)
    }

    fun onItemClicked(name: String) {
        executeCommand(ShowSnackbar(name))
    }

    /**
     * [msg] - info message for a user.
     *         Fires in case of no internet connection
     * */
    private fun loadCharacters(msg: String) {
        updateScreenState(
            model,
            model.dataList,
            errorLayoutVisibilityState = false,
            progressBarVisibilityState = true,
            shouldRefreshView = true
        ) // this is so fast in no internet connection case, that it looks like a glitch

        repository.getCharacters(object : Resource {
            override fun onError() {
                if (msg.isNotEmpty()) {
                    executeCommand(ShowSnackbar(msg))
                }
                updateScreenState(
                    model,
                    model.dataList,
                    errorLayoutVisibilityState = true,
                    progressBarVisibilityState = false,
                    shouldRefreshView = true
                )
            }

            override fun onSuccess(data: List<SimpleCharacter>) {
                updateScreenState(
                    model,
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