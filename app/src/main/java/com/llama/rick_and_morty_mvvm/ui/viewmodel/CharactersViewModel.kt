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
        isMaleChipSelected: Boolean = screenState.isMaleChipSelected,
        isGenderlessChipSelected: Boolean = screenState.isGenderlessChipSelected,
        isUnknownChipSelected: Boolean = screenState.isUnknownChipSelected,
        shouldRefreshView: Boolean = true
    ) {
        this.screenState = CharactersScreenState(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState,
            chipsGroupVisibilityState,
            isBtnRetryClicked,
            isFemaleChipSelected,
            isMaleChipSelected,
            isGenderlessChipSelected,
            isUnknownChipSelected
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    // у меня есть данные в list. я где-то на уровне бизнес-логики разделяю их по спискам по полу.
    // и когда нажимаю чипсины, просто дергаю нужный список, добавляя или удаляя его из результирующего
    private fun getFemales(): List<SimpleCharacter> =
        list.filter { it.gender == "Female" }

    private fun getMales(): List<SimpleCharacter> =
        list.filter { it.gender == "Male" }

    private fun getGenderless(): List<SimpleCharacter> =
        list.filter { it.gender == "Genderless" }

    private fun getCharactersWithUnknownGender(): List<SimpleCharacter> =
        list.filter { it.gender == "Unknown" }

    // move to business logic level
    private fun filterListByGender(genders: List<String>): List<SimpleCharacter> {
        var females: List<SimpleCharacter> = emptyList()
        var males: List<SimpleCharacter> = emptyList()
        var genderless: List<SimpleCharacter> = emptyList()
        var unknown: List<SimpleCharacter> = emptyList()
        genders.forEach { gender ->
            when(gender) {
                "Female" -> females = getFemales()
                "Male" -> males = getMales()
                "Genderless" -> genderless = getGenderless()
                "Unknown" -> unknown = getCharactersWithUnknownGender()
            }
        }
        Log.d(TAG, "filterListByGender: females = ${females.size}\n," +
                "males = ${males.size}\n, genderless = ${genderless.size}\n," +
                "unknown = ${unknown.size}\n, ")
        val result = listOf(females, males, genderless, unknown).flatten().sortedBy { it.id }
        return if (genders.isEmpty()) list else result // check if there is a bug,
        // when 50 items are loaded. Now can't decide if the bug is present
    }



    fun onChipChecked(genders: List<String>) {
        val filteredDataList = filterListByGender(genders)
        when {
            genders.contains("Female") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isFemaleChipSelected = true
                )
            }
            genders.contains("Male") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isMaleChipSelected = true
                )
            }
            genders.contains("Genderless") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isGenderlessChipSelected = true
                )
            }
            genders.contains("Unknown") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isUnknownChipSelected = true
                )
            }
        }
    }

    fun onChipUnchecked(genders: List<String>) {
        val filteredDataList = filterListByGender(genders)
        when {
            !genders.contains("Female") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isFemaleChipSelected = false
                )
            }
            !genders.contains("Male") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isMaleChipSelected = false
                )
            }
            !genders.contains("Genderless") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isGenderlessChipSelected = false
                )
            }
            !genders.contains("Unknown") -> {
                updateScreenState(
                    dataListState = filteredDataList,
                    isUnknownChipSelected = false
                )
            }
        }
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