package com.llama.rick_and_morty_mvvm.ui.viewmodel

import com.llama.rick_and_morty_mvvm.BuildConfig
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState

class CharacterDetailsViewModel(
    screenState: CharacterDetailsScreenState,
    private val interactor: CharactersInteractor
) : BaseViewModel<
        CharacterDetailsScreenState,
        DetailsCommand>(screenState) {

    private var character: SimpleCharacter? = null

    private fun updateScreenState(
        screenState: CharacterDetailsScreenState = this.screenState,
        characterState: SimpleCharacter? = screenState.character
    ) {
        this.screenState = CharacterDetailsScreenState(
            characterState
        )
        refreshView()
    }

    private fun onUrlClicked() {
        character?.let {
            executeCommand(OpenLink(it.image))
        }
    }

    fun getCharacter(id: Int) {
        character = interactor.getCachedData().firstOrNull { it.id == id }
        updateScreenState(characterState = character)
    }

    fun toggleWebViewFeature() {
        if (BuildConfig.IS_WEB_VIEW_FEATURE_ON) {
            onUrlClicked()
        } // can't do else branch here, because I'll drag `android.*` to VM which is as bad as drag it to Presenter
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "CharacterDetailsFragment"
    }

}