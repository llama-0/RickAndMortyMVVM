package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.FetchDataCallback
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.Navigate
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.model.GenderFilter
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModel(
    private val interactor: CharactersInteractor,
    screenState: CharactersScreenState,
    private val resources: Resources,
    private val bundle: Bundle
) : BaseViewModel<
        CharactersScreenState,
        CharactersCommand>(screenState) {

    private lateinit var characters: List<SimpleCharacter>

    private val gender: GenderFilter by lazy {
        GenderFilter(resources, characters)
    }

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
        isGenderChipSelected: Boolean = screenState.isGenderChipSelected,
        shouldRefreshView: Boolean = true
    ) {
        this.screenState = CharactersScreenState(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState,
            chipsGroupVisibilityState,
            isBtnRetryClicked,
            isGenderChipSelected
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    private fun loadCharacters() {
        updateScreenState(progressBarVisibilityState = true)

        interactor.fetchData(object : FetchDataCallback {
            override fun onSuccess(data: List<SimpleCharacter>) {
                characters = data
                updateScreenState(
                    dataListState = data,
                    errorLayoutVisibilityState = false,
                    progressBarVisibilityState = false,
                    chipsGroupVisibilityState = true,
                    isBtnRetryClicked = false
                )
            }

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

        })
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(isBtnRetryClicked = true)
    }

    fun onChipChecked(genders: List<String>) {
        val filteredDataList: List<SimpleCharacter> = gender.filterByGender(genders)
        updateScreenState(
            dataListState = filteredDataList,
            isGenderChipSelected = true
        )
    }

    fun onChipUnchecked(genders: List<String>) {
        val filteredDataList: List<SimpleCharacter> = gender.filterByGender(genders)
        updateScreenState(
            dataListState = filteredDataList,
            isGenderChipSelected = false
        )
    }

    fun onItemClicked(id: Int) {
        bundle.putInt(INT_CHARACTER_ID_KEY, id)
        executeCommand(
            Navigate(R.id.navigationCharacterDetails, bundle)
        )
    }

    companion object {
        private const val TAG = "CharactersViewModel"
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }
}