package com.llama.rick_and_morty_mvvm.ui.view.screenstate

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseScreenState

class CharacterDetailsScreenState(
    val character: SimpleCharacter
) : BaseScreenState