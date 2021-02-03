package com.llama.rick_and_morty_mvvm.ui.view.screenstate

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseScreenState
import com.llama.rick_and_morty_mvvm.ui.model.GenderType

class CharactersScreenState(
    val dataList: List<SimpleCharacter> = emptyList(),
    val errorLayoutVisibility: Boolean = false,
    val progressBarVisibility: Boolean = true,
    val chipsGroupVisibility: Boolean = false, // will be true, when I fix chips layout
    val isBtnRetryClicked: Boolean = false,
    val checkedGenderTypes: MutableSet<GenderType> = mutableSetOf()
) : BaseScreenState