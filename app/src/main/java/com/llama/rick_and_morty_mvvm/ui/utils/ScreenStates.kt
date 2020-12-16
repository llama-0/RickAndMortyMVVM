package com.llama.rick_and_morty_mvvm.ui.utils

import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/**
 * A class for `screen states` (aka `view states`)
 * */
sealed class RefreshableScreenState<out T : Any>

class HomeScreenState<out T : Any>(
    val dataList: List<SimpleCharacter>,
    val errorLayoutVisibility: Boolean,
    val progressBarVisibility: Boolean
) : RefreshableScreenState<T>()