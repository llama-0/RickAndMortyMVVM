package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.SharedPreferences
import android.content.res.Resources
import android.util.Log
import androidx.annotation.UiThread
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.interactor.CharactersInteractor
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

    // todo: fix UI inhibition -- can't update UI on time since implemented interactor
    private fun loadCharacters() {
        updateScreenState(progressBarVisibilityState = true)

        interactor.fetchData()

        if (interactor.getErrorState()) { // экран ошибки теперь работает иногда... но когда отработал, то обновить экран до состояния списка уже не может
            Log.d(TAG, "loadCharacters: error == true should it be")
            if (screenState.isBtnRetryClicked) {
                executeCommand(ShowSnackbar(resources.getString(R.string.check_internet_connection_message)))
            }
            updateScreenState(
                errorLayoutVisibilityState = true,
                progressBarVisibilityState = false,
                chipsGroupVisibilityState = false
            )
        } else {
            list = interactor.getFetchedData()
            Log.d(TAG, "loadCharacters: list_size == ${list.size}")
            list.forEach { Log.d(TAG, "loadCharacters: $it") }
            updateScreenState(
                dataListState = list,
                errorLayoutVisibilityState = false,
                progressBarVisibilityState = false,
                chipsGroupVisibilityState = true
            )
        }
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

    @Suppress("unused")
    companion object {
        private const val TAG = "TAG"
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
        private const val OBJ_CHARACTER_KEY = "OBJ_CHARACTER_KEY"
    }
}