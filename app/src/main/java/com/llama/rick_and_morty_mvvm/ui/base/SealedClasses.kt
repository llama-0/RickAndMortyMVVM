package com.llama.rick_and_morty_mvvm.ui.base

/**
 * A class for `screen states` (aka `view states`)
 * */
sealed class RefreshableScreenState<out T : Any>
class Success<out T : Any>(val data: T) : RefreshableScreenState<T>()
class Error<out T : Any> : RefreshableScreenState<T>() // can have `val error: Throwable`

//class Loading<out T : Any> : RefreshableScreenState<T>() // my loading is just a liveData
class NoInternetState<T : Any> : RefreshableScreenState<T>()


/*
* Tip. Declare commands without parameters as `object`
*      to avoid having to create new instances of them
* */
sealed class Command
object ClickButton : Command()
class ShowSnackbar(val message: String) : Command()
//    class Navigate(val destinationId: Int) : Command()
//    object SwipeRefresh : Command()