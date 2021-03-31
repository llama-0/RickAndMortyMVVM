package com.llama.rick_and_morty_mvvm.presentation.viewmodels

import com.llama.rick_and_morty_mvvm.BuildConfig
import com.llama.rick_and_morty_mvvm.domain.interactors.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter
import com.llama.rick_and_morty_mvvm.presentation.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.presentation.commands.DetailsCommand
import com.llama.rick_and_morty_mvvm.presentation.commands.DetailsCommand.OpenLinkInBrowser
import com.llama.rick_and_morty_mvvm.presentation.commands.DetailsCommand.OpenLinkInWebView
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharacterDetailsScreenState

class CharacterDetailsViewModel(
    screenState: CharacterDetailsScreenState,
    private val interactor: CharactersInteractor
) : BaseViewModel<
        CharacterDetailsScreenState,
        DetailsCommand>(screenState) {

    private var character: SimpleCharacter? = null

    @Synchronized
    private fun updateScreenState(
        screenState: CharacterDetailsScreenState = this.screenState,
        characterState: SimpleCharacter? = screenState.character
    ) {
        this.screenState = CharacterDetailsScreenState(
            characterState
        )
        refreshView()
    }

    fun getCharacter(id: Int) {
        character = interactor.getCachedData().firstOrNull { simpleCharacter ->
            simpleCharacter.id == id
        }
        updateScreenState(characterState = character)
    }

    fun addCharacter() {
//        TODO()
    }

    fun onUrlClicked() {
        with(character ?: return) {
            if (BuildConfig.IS_WEB_VIEW_FEATURE_ON) {
                executeCommand(OpenLinkInWebView(image))
            } else {
                executeCommand(OpenLinkInBrowser)
            }
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "CharacterDetailsViewModel"
    }
}