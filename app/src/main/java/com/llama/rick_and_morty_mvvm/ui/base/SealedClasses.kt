package com.llama.rick_and_morty_mvvm.ui.base

import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/**
 * A class for `screen states` (aka `view states`)
 * */
sealed class RefreshableScreenState<out T : Any>
class HomeScreenState<out T : Any>(
    val dataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData(),
    val errorLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(),
    val progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData()
) : RefreshableScreenState<T>()

sealed class Command
class ShowSnackbar(var message: String) : Command()
//object ClickButton : Command()
//class Navigate(val destinationId: Int) : Command()
//object SwipeRefresh : Command()