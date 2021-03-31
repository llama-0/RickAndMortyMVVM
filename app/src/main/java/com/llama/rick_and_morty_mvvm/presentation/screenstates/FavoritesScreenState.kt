package com.llama.rick_and_morty_mvvm.presentation.screenstates

import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter
import com.llama.rick_and_morty_mvvm.presentation.base.BaseScreenState

class FavoritesScreenState(
    val dataList: List<SimpleCharacter> = emptyList(),
    val isListEmpty: Boolean = true,
) : BaseScreenState