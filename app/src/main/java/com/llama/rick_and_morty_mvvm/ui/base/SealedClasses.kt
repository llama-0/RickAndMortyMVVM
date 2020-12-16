package com.llama.rick_and_morty_mvvm.ui.base

import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData

/**
 * A class for `screen states` (aka `view states`)
 * */
sealed class RefreshableScreenState<out T : Any>
class HomeScreenState<out T : Any>(
    val dataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData(),
    val errorLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(),
    val progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData(),
    val isSnackbarActionRequired: MutableLiveData<Boolean> = SingleEventLiveData() // у нас тепепрь есть комманды, мб здесь уже просто MutableLiveData юзать
) : RefreshableScreenState<T>()

/*
* Tip. Declare commands without parameters as `object`
*      to avoid having to create new instances of them
* */
sealed class Command
object ClickButton : Command()
class ShowSnackbar(var message: String) : Command()
//    class Navigate(val destinationId: Int) : Command()
//    object SwipeRefresh : Command()