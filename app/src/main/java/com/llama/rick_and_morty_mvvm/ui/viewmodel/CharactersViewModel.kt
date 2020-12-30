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
    private val repository: RepositoryImpl, // выпилить потом, если сработает идея
    private val sharedPrefs: SharedPreferences,
    private val interactor: CharactersInteractor,
    screenState: CharactersScreenState,
    private val resources: Resources
) : BaseViewModel<
        CharactersScreenState,
        BaseCommand>(screenState) {

    private lateinit var list: List<SimpleCharacter>
    private val gender by lazy {
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
        interactor.fetchData()
        Thread.sleep(1000L) // todo; rewrite
        // сейчас я таким образом жду, пока результат загрузится из сети
        // иначе UI не успеет отреагировать. Раньше LiveData отвечала за "ожидание"
        // теперь есть интерактор между загрузкой данных и LiveData и надо как-то иначе ждать

        updateScreenState(progressBarVisibilityState = true) // вот это теперь не работает

        Log.d(TAG, "loadCharacters: after interactor.fetch() called")

        if (interactor.isError) { // экран ошибки теперь работает иногда... но когда отработал, то обновить экран до состояния списка уже не может
            Log.d(TAG, "loadCharacters: error == ${interactor.isError}")
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

    // send data to details fragment: bundleOf + character_obj
//    fun onItemClicked(character: SimpleCharacter) {
//        val gson = Gson()
//        val jsonString = gson.toJson(character)
//        val bundle = bundleOf(OBJ_CHARACTER_KEY to jsonString)
//        executeCommand(
//            Navigate(R.id.navigationCharacterDetails, bundle)
//        )
//    }

    @Suppress("unused")
    companion object {
        private const val TAG = "TAG"
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
        private const val OBJ_CHARACTER_KEY = "OBJ_CHARACTER_KEY"
    }
}