package com.llama.rick_and_morty_mvvm.ui.viewmodel

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.view.CharacterDetailsScreenState

class CharacterDetailsViewModel(
    screenState: CharacterDetailsScreenState
) : BaseViewModel<
        CharacterDetailsScreenState,
        BaseCommand>(screenState) {

    init {
//        updateScreenState(characterState = character)
    }

    // 1. get character from `model`
    // 2. link this character with the character of screenState

    // в этом фрагменте screenState вообще не нужен, он просто показывает статические данные, полученные по id из недр пока не известно чего
    private fun updateScreenState(
        screenState: CharacterDetailsScreenState = this.screenState,
        characterState: SimpleCharacter = screenState.character
    ) {
        this.screenState = CharacterDetailsScreenState(characterState)
        refreshView()
    }

    fun onUrlClicked() {
//        executeCommand(OpenLink(character.image))
    }

}