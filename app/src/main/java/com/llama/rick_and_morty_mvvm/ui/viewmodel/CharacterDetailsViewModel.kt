package com.llama.rick_and_morty_mvvm.ui.viewmodel

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.Command
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.CharacterDetailsScreenState

class CharacterDetailsViewModel(
    private val character: SimpleCharacter,
    screenState: CharacterDetailsScreenState
) : BaseViewModel<
        CharacterDetailsScreenState,
        Command>(screenState) {

    init {
        showScreenState(
            screenState,
            character
        )
    }

    private fun showScreenState(
        screenState: CharacterDetailsScreenState = this.screenState,
        characterState: SimpleCharacter = screenState.character
    ) {
        this.screenState = CharacterDetailsScreenState(characterState)
        refreshView()
    }

    fun onUrlClicked() {
        executeCommand(OpenLink(character.image))
    }

}