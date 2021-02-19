package com.llama.rick_and_morty_mvvm.ui.viewmodel

import com.llama.rick_and_morty_mvvm.domain.interactor.FavoritesInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.FavoritesCommand
import com.llama.rick_and_morty_mvvm.ui.command.FavoritesCommand.OpenDetailsScreen
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.FavoritesScreenState

class FavoritesViewModel(
    screenState: FavoritesScreenState,
    private val interactor: FavoritesInteractor
) : BaseViewModel<
        FavoritesScreenState,
        FavoritesCommand>(screenState) {


    private var list: List<SimpleCharacter>? = null

    init {
        loadCharacters()
    }

    @Synchronized
    private fun updateScreenState(
        screenState: FavoritesScreenState = this.screenState,
        dataListState: List<SimpleCharacter> = screenState.dataList,
        isListEmpty: Boolean = screenState.isListEmpty,
    ) {
        this.screenState = FavoritesScreenState(
            dataListState,
            isListEmpty,
        )
        refreshView()
    }

    private fun loadCharacters() {
        list = interactor.getFavorites()
        list?.let {
            if (it.isEmpty()) {
                updateScreenState(isListEmpty = true)
            } else {
                updateScreenState(
                    dataListState = it,
                    isListEmpty = false
                )
            }
        }
    }

    override fun onItemClicked(id: Int) {
        executeCommand(
            OpenDetailsScreen(id)
        )
    }

    companion object {
        private const val TAG = "FavoriteViewModel"
    }
}