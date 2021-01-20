package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.domain.FetchDataCallback
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.*
import com.llama.rick_and_morty_mvvm.ui.mapper.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.ui.model.GenderFilter
import com.llama.rick_and_morty_mvvm.ui.model.GenderType
import com.llama.rick_and_morty_mvvm.ui.model.GenderType.*
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModel(
    private val interactor: CharactersInteractor,
    screenState: CharactersScreenState,
    private val resources: Resources
) : BaseViewModel<
        CharactersScreenState,
        CharactersCommand>(screenState) {

    private lateinit var chipIdsToGenderTypes: List<Pair<Int, GenderType>> // lateinit var should be replaced
    private var chipsState: List<Boolean> = listOf(
        screenState.isFemaleChipSelected,
        screenState.isMaleChipSelected,
        screenState.isGenderlessChipSelected,
        screenState.isUnknownChipSelected
    )

    private lateinit var characters: List<SimpleCharacter> // bad
    private lateinit var filteredList: List<SimpleCharacter> // bad

    private val gender: GenderFilter by lazy { // bad
        GenderFilter(resources, characters, filteredList)
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
        isFemaleChipSelected: Boolean = screenState.isFemaleChipSelected,
        isMaleChipSelected: Boolean = screenState.isMaleChipSelected,
        isGenderlessChipSelected: Boolean = screenState.isGenderlessChipSelected,
        isUnknownChipSelected: Boolean = screenState.isUnknownChipSelected,
//        isGenderChipSelected: Boolean = screenState.isGenderChipSelected,
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
//            isGenderChipSelected
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

    fun mapChipsIds(ids: List<Int>) {
        chipIdsToGenderTypes = ChipIdToGenderType().map(ids)
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(isBtnRetryClicked = true)
    }

//    fun onChipClicked(id: Int, isChecked: Boolean) {
//
//    }


    fun onChipClicked(type: GenderType) {
        filteredList = gender.filter(type, chipsState)
        updateScreenState(dataListState = filteredList)
        if (type == FEMALE) {
            if (screenState.isFemaleChipSelected) {
                updateScreenState(
                    dataListState = filteredList,
                    isFemaleChipSelected = false
                )
            } else {
                updateScreenState(
                    dataListState = filteredList,
                    isFemaleChipSelected = true
                )
            }
        }
//        when (type) {
//            FEMALE -> updateScreenState(
//                dataListState = filteredDataList,
//                isFemaleChipSelected = true
//            )
//            MALE -> updateScreenState(
//                dataListState = filteredDataList,
//                isMaleChipSelected = true
//            )
//            GENDERLESS -> updateScreenState(
//                dataListState = filteredDataList,
//                isGenderlessChipSelected = true
//            )
//            UNKNOWN -> updateScreenState(
//                dataListState = filteredDataList,
//                isUnknownChipSelected = true
//            )
//        }
    }

//    fun onChipChecked(genders: List<String>) {
//        val filteredDataList: List<SimpleCharacter> = gender.filterByGender(genders)
//        updateScreenState(
//            dataListState = filteredDataList,
////            isGenderChipSelected = true
//        )
//    }
//
//    fun onChipUnchecked(genders: List<String>) {
//        val filteredDataList: List<SimpleCharacter> = gender.filterByGender(genders)
//        updateScreenState(
//            dataListState = filteredDataList,
////            isGenderChipSelected = false
//        )
//    }

    fun onItemClicked(id: Int) {
        executeCommand(
            OpenDetailsScreen(id)
        )
    }

    companion object {
        private const val TAG = "CharactersViewModel"
    }
}