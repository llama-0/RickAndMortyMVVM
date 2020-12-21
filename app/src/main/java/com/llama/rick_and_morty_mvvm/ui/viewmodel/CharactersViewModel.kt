package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback
import com.llama.rick_and_morty_mvvm.domain.model.FilterByGender
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

    private fun updateScreenState(
        screenState: CharactersScreenState = this.screenState,
        dataListState: List<SimpleCharacter> = screenState.dataList,
        errorLayoutVisibilityState: Boolean = screenState.errorLayoutVisibility,
        progressBarVisibilityState: Boolean = screenState.progressBarVisibility,
        chipsGroupVisibilityState: Boolean = screenState.chipsGroupVisibility,
        isBtnRetryClicked: Boolean = screenState.isBtnRetryClicked,
        isFemaleChipSelected: Boolean = screenState.isFemaleChipSelected,
        shouldRefreshView: Boolean = true
    ) {
        this.screenState = CharactersScreenState(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState,
            chipsGroupVisibilityState,
            isBtnRetryClicked,
            isFemaleChipSelected
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    fun onChipSelected(gender: String) {
        val filteredDataList = FilterByGender(screenState.dataList, gender).filterByGender()
        updateScreenState(
            dataListState = filteredDataList,
            isFemaleChipSelected = true
        )
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(isBtnRetryClicked = true)
    }

    fun onItemClicked(name: String) {
        executeCommand(ShowSnackbar(name))
    }

    private fun loadCharacters() {
        updateScreenState(progressBarVisibilityState = true)

        repository.getCharacters(object : FetchRemoteDataCallback {
            override fun onError() {
                if (screenState.isBtnRetryClicked) {
                    executeCommand(ShowSnackbar(resources.getString(R.string.check_internet_connection_message)))
                }
                updateScreenState(
                    errorLayoutVisibilityState = true,
                    progressBarVisibilityState = false,
                    chipsGroupVisibilityState = false
                )
            }

            override fun onSuccess(data: List<SimpleCharacter>) {
                updateScreenState(
                    dataListState = data,
                    errorLayoutVisibilityState = false,
                    progressBarVisibilityState = false,
                    chipsGroupVisibilityState = true
                )
            }
        })
    }

    companion object {
        private const val TAG = "TAG"
    }
}