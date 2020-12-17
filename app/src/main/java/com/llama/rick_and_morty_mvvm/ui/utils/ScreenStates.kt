package com.llama.rick_and_morty_mvvm.ui.utils

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter


interface BaseScreenState

class HomeScreenState(
    val dataList: List<SimpleCharacter>,
    val errorLayoutVisibility: Boolean,
    val progressBarVisibility: Boolean
) : BaseScreenState