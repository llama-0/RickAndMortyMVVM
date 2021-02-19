package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.FetchCharactersListCallback
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.*
import com.llama.rick_and_morty_mvvm.ui.mapper.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.ui.model.GenderFilter
import com.llama.rick_and_morty_mvvm.ui.model.GenderType
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState

class CharactersViewModel(
    private val interactor: CharactersInteractor,
    screenState: CharactersScreenState,
    private val chipIdToGenderType: ChipIdToGenderType,
    private val resources: Resources
) : BaseViewModel<
        CharactersScreenState,
        CharactersCommand>(screenState) {

    private var list: List<SimpleCharacter>? = null
    private var chipsIdsToGenderTypes: List<Pair<Int, GenderType>>? = null

    init {
        loadCharacters()
    }

    @Synchronized
    private fun updateScreenState(
        screenState: CharactersScreenState = this.screenState,
        dataListState: List<SimpleCharacter> = screenState.dataList,
        errorLayoutVisibilityState: Boolean = screenState.errorLayoutVisibility,
        progressBarVisibilityState: Boolean = screenState.progressBarVisibility,
        chipsGroupVisibilityState: Boolean = screenState.chipsGroupVisibility,
        isBtnRetryClicked: Boolean = screenState.isBtnRetryClicked,
        checkedGenderTypes: MutableSet<GenderType> = screenState.checkedGenderTypes,
        shouldRefreshView: Boolean = true
    ) {
        this.screenState = CharactersScreenState(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState,
            chipsGroupVisibilityState,
            isBtnRetryClicked,
            checkedGenderTypes
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    private fun loadCharacters() {
        updateScreenState(progressBarVisibilityState = true)

        interactor.fetchData(object : FetchCharactersListCallback {
            override fun onSuccess(data: List<SimpleCharacter>) {
                list = data
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

    private fun updateCheckedGenderTypes(genderType: GenderType) {
        // как в задче со скобочками. Если среди screenState.checkedGenderTypes уже есть тип == genderType,
        // значит мы второй раз нажимаем на чипсину, значит мы должны отселектить её (и удалить из списка)
        // screenState.checkedGenderTypes.delete(genderType)
        // а если такого типа ещё нет, то добавить его.
        if (!screenState.checkedGenderTypes.contains(genderType)) {
            screenState.checkedGenderTypes.add(genderType)
        } else {
            screenState.checkedGenderTypes.remove(genderType)
        }
    }

    fun mapChipIdsToGenderTypes(ids: List<Int>) {
        chipsIdsToGenderTypes = chipIdToGenderType.map(ids)
    }

    private fun updateCheckedGenderTypes(id: Int) {
        // как в задче со скобочками. Если среди screenState.checkedGenderTypes уже есть тип == genderType,
        // значит мы второй раз нажимаем на чипсину, значит мы должны отселектить её (и удалить из списка)
        // screenState.checkedGenderTypes.delete(genderType)
        // а если такого типа ещё нет, то добавить его.
        val genderType: GenderType = chipsIdsToGenderTypes?.find {
            it.first == id
        }?.second ?: return
        if (!screenState.checkedGenderTypes.contains(genderType)) {
            screenState.checkedGenderTypes.add(genderType)
        } else {
            screenState.checkedGenderTypes.remove(genderType)
        }
    }

    fun onChipClicked(id: Int) {
        updateCheckedGenderTypes(id)
        val filteredList: List<SimpleCharacter>? =
            list?.let {
                GenderFilter(resources, it, screenState.checkedGenderTypes)
                    .filter()
            }
        filteredList?.let { updateScreenState(dataListState = it) }
    }

    fun onChipClicked(genderType: GenderType) {
        updateCheckedGenderTypes(genderType)
        val filteredList: List<SimpleCharacter>? =
            list?.let {
                GenderFilter(resources, it, screenState.checkedGenderTypes)
                    .filter()
            }
        filteredList?.let { updateScreenState(dataListState = it) }
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(isBtnRetryClicked = true)
    }

    override fun onItemClicked(id: Int) {
        executeCommand(
            OpenDetailsScreen(id)
        )
    }

    fun onFavoriteClicked() {
        executeCommand(
            OpenFavoritesScreen
        )
    }

    companion object {
        private const val TAG = "CharactersViewModel"
    }
}