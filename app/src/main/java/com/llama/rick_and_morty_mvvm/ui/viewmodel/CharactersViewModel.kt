package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.OpenDetailsScreen
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.mapper.ChipIdToGenderType
import com.llama.rick_and_morty_mvvm.ui.model.GenderFilter
import com.llama.rick_and_morty_mvvm.ui.model.GenderType
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

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

    private var disposable: Disposable? = null

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
        disposable = interactor.fetchData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<SimpleCharacter>>() {
                override fun onSuccess(t: List<SimpleCharacter>?) {
                    Log.d(TAG, "Success ----------------- list size = ${t?.size}")
                    list = t
                    updateScreenState(
                        dataListState = t ?: return,
                        errorLayoutVisibilityState = false,
                        progressBarVisibilityState = false,
                        chipsGroupVisibilityState = true,
                        isBtnRetryClicked = false
                    )

                }

                override fun onError(e: Throwable?) {
                    Log.e(TAG, "onError: getCharacters from remote failed", e)
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

    // todo: probably need to move disposable to BaseVM and make it `protected` and onCleared() method also move there
    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable?.dispose()
            disposable = null
        }
    }

    fun mapChipIdsToGenderTypes(ids: List<Int>) {
        chipsIdsToGenderTypes = chipIdToGenderType.map(ids)
    }

    private fun updateCheckedGenderTypes(id: Int) {
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

    fun onButtonRetryClicked() {
        loadCharacters()
        updateScreenState(isBtnRetryClicked = true)
    }

    fun onItemClicked(id: Int) {
        executeCommand(
            OpenDetailsScreen(id)
        )
    }

    companion object {
        private const val TAG = "CharactersViewModel"
    }
}