package com.llama.rick_and_morty_mvvm.ui.base

import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/**
 * A class for `screen states` (aka `view states`)
 * */
sealed class RefreshableScreenState<out T : Any>
class HomeScreenState<out T : Any>(
    val dataList: MutableLiveData<List<SimpleCharacter>>,
    val errorLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(),
    val progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData()
) : RefreshableScreenState<T>()

/*
* Tip. Declare commands without parameters as `object`
*      to avoid having to create new instances of them
* */
sealed class Command
object ClickButton : Command()
class ShowSnackbar(val message: String) : Command()
//    class Navigate(val destinationId: Int) : Command()
//    object SwipeRefresh : Command()

// вот это куда теперь? (раньше было в стейт, а теперь надо отдельно обрабатывать как action aka command)
//val snackbarAction: MutableLiveData<Boolean> = SingleEventLiveData(),
//val isRetryButtonClicked: MutableLiveData<Boolean> = MutableLiveData()