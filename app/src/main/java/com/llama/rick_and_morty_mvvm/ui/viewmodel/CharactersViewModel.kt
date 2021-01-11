package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.FetchDataInnerCallback
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.Navigate
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.model.Gender
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModel(
    private val sharedPrefs: SharedPreferences,
    private val interactor: CharactersInteractor,
    screenState: CharactersScreenState,
    private val resources: Resources
) : BaseViewModel<
        CharactersScreenState,
        BaseCommand>(screenState) {

    private lateinit var list: List<SimpleCharacter>

    private val gender: Gender by lazy {
        Gender(resources, list)
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

        interactor.fetchData(object : FetchDataInnerCallback {
            override fun onSuccess(data: List<SimpleCharacter>) {
                list = data
                updateScreenState(
                    dataListState = list,
                    errorLayoutVisibilityState = false,
                    progressBarVisibilityState = false,
                    chipsGroupVisibilityState = true
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
        val filteredDataList: List<SimpleCharacter> = gender.filterListByGender(genders)
        updateScreenState(
            dataListState = filteredDataList,
            isGenderChipSelected = true
        )
    }

    fun onChipUnchecked(genders: List<String>) {
        val filteredDataList: List<SimpleCharacter> = gender.filterListByGender(genders)
        updateScreenState(
            dataListState = filteredDataList,
            isGenderChipSelected = false
        )
    }

    fun onItemClicked(id: Int) {
        sharedPrefs.edit().putInt(INT_CHARACTER_ID_KEY, id).apply()
        executeCommand(
            Navigate(R.id.navigationCharacterDetails)
        )
    }

    companion object {
        private const val TAG = "TAG"
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }
}