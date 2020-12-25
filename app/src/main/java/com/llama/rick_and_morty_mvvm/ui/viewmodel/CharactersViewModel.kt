package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.FetchRemoteDataCallback
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.Navigate
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.view.CharactersScreenState

class CharactersViewModel(
    private val repository: RepositoryImpl,
    screenState: CharactersScreenState,
    private val resources: Resources
) : BaseViewModel<
        CharactersScreenState,
        BaseCommand>(screenState) {

    private lateinit var list: List<SimpleCharacter>

    private val femaleApiField = resources.getString(R.string.female_gender_api_field)
    private val maleApiField = resources.getString(R.string.male_gender_api_field)
    private val genderlessApiField = resources.getString(R.string.genderless_gender_api_field)
    private val unknownApiField = resources.getString(R.string.unknown_gender_api_field)

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

    // move to business logic level
    private fun getFemales(): List<SimpleCharacter> =
        list.filter { it.gender == femaleApiField }

    private fun getMales(): List<SimpleCharacter> =
        list.filter { it.gender == maleApiField }

    private fun getGenderless(): List<SimpleCharacter> =
        list.filter { it.gender == genderlessApiField }

    private fun getCharactersWithUnknownGender(): List<SimpleCharacter> =
        list.filter { it.gender.equals(unknownApiField, true) }

    private fun filterListByGender(genders: List<String>): List<SimpleCharacter> {
        var females: List<SimpleCharacter> = emptyList()
        var males: List<SimpleCharacter> = emptyList()
        var genderless: List<SimpleCharacter> = emptyList()
        var unknown: List<SimpleCharacter> = emptyList()
        genders.forEach { gender ->
            when(gender) {
                femaleApiField -> females = getFemales()
                maleApiField -> males = getMales()
                genderlessApiField -> genderless = getGenderless()
                unknownApiField -> unknown = getCharactersWithUnknownGender()
            }
        }
        val result = listOf(females, males, genderless, unknown).flatten().sortedBy { it.id }
        return if (genders.isEmpty()) list else result
    }

    fun onChipChecked(genders: List<String>) {
        val filteredDataList = filterListByGender(genders)
        updateScreenState(
            dataListState = filteredDataList,
            isGenderChipSelected = true
        )
    }

    fun onChipUnchecked(genders: List<String>) {
        val filteredDataList = filterListByGender(genders)
        updateScreenState(
            dataListState = filteredDataList,
            isGenderChipSelected = false
        )
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(isBtnRetryClicked = true)
    }

    fun onItemClicked(id: Int) {
        // save id to `some model` in order to get character by this id from a list of characters stored in `another model` visible in second fragment
        executeCommand(
            Navigate(R.id.navigationCharacterDetails)
        )
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
                list = data

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
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }
}