package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.content.SharedPreferences
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState

class CharacterDetailsViewModel(
    screenState: CharacterDetailsScreenState,
    sharedPrefs: SharedPreferences,
    interactor: CharactersInteractor
) : BaseViewModel<
        CharacterDetailsScreenState,
        BaseCommand>(screenState) {

    private val characterId: Int = sharedPrefs.getInt(INT_CHARACTER_ID_KEY, -1)
    private val character: SimpleCharacter? =
        interactor.getFetchedData().firstOrNull { it.id == characterId }

    init {
        character?.let { updateScreenState(characterState = it) }
    }

    private fun updateScreenState(
        screenState: CharacterDetailsScreenState = this.screenState,
        characterState: SimpleCharacter = screenState.character
    ) {
        this.screenState = CharacterDetailsScreenState(
            characterState
        )
        refreshView()
    }

    @Suppress("unused")
    fun onUrlClicked() {
        character?.let {
            executeCommand(OpenLink(it.image))
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "TAG"
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }

}