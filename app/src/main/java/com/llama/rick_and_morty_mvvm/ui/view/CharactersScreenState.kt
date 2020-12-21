package com.llama.rick_and_morty_mvvm.ui.view

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseScreenState


class CharactersScreenState(
    val dataList: List<SimpleCharacter>,
    val errorLayoutVisibility: Boolean,
    val progressBarVisibility: Boolean,
    val chipsGroupVisibility: Boolean,
    val isBtnRetryClicked: Boolean,
    val isFemaleChipSelected: Boolean
) : BaseScreenState