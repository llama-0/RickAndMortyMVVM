package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.os.Bundle
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.fragment.CharacterDetailsFragmentArgs
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState

class CharacterDetailsViewModel(
    screenState: CharacterDetailsScreenState,
    interactor: CharactersInteractor,
    bundle: Bundle
) : BaseViewModel<
        CharacterDetailsScreenState,
        DetailsCommand>(screenState) {

    private val characterId: Int = CharacterDetailsFragmentArgs.fromBundle(bundle).characterId // -1 which is default, and I still need bundle so it's not right

//    private val characterId: Int = bundle.getInt(INT_CHARACTER_ID_KEY)
    private val character: SimpleCharacter? =
        interactor.getCachedData().firstOrNull { it.id == characterId }

    init {
        character?.let {
            updateScreenState(characterState = it)
        }
    }

    private fun updateScreenState(
        screenState: CharacterDetailsScreenState = this.screenState,
        characterState: SimpleCharacter? = screenState.character
    ) {
        this.screenState = CharacterDetailsScreenState(
            characterState
        )
        refreshView()
    }

    fun onUrlClicked() {
        character?.let {
            executeCommand(OpenLink(it.image))
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "CharacterDetailsFragment"
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }

}