package com.llama.rick_and_morty_mvvm.ui.base

import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData

abstract class BaseFragment<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command,
        ViewModel : BaseViewModel<ScreenState, Command>> ( // why name the BaseViewModel Refreshable ?
    @LayoutRes val layoutResId: Int,
            viewModelClass: Class<ViewModel>
) : Fragment(layoutResId) {

}

/*
* a class for `screen states` (aka `view states`)
* */
sealed class RefreshableScreenState<out T : Any>
class Success<out T : Any>(val data: T) : RefreshableScreenState<T>()
class Error<out T : Any> : RefreshableScreenState<T>() // can have `val error: Throwable`
//class Loading<out T : Any> : RefreshableScreenState<T>() // my loading is just a liveData
class NoInternetState<T : Any> : RefreshableScreenState<T>()


// Tip. Declare commands without parameters as `object`
//      to avoid having to create new instances of them
sealed class Command
object ClickButton : Command()
class ShowSnackbar(val message: String) : Command()
//    class Navigate(val destinationId: Int) : Command()
//    object SwipeRefresh : Command()

abstract class BaseViewModel<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command>(
//        val repository: RepositoryImpl // should I place it here or directly in HomeViewModel?
//                                          (I think directly into HomeViewModel)
) : ViewModel() {

    private val modelUpdateEvent = MutableLiveData<ScreenState>()
    private val commandsMutableLiveData: MutableLiveData<SupportedCommandType> =
            SingleEventLiveData()

    val commandsLiveData: LiveData<SupportedCommandType> = commandsMutableLiveData
    val modelUpdate: LiveData<ScreenState> = modelUpdateEvent

    // protected may cause "Kotlin: Cannot access 'executeCommand': it is protected"
    // in child classes
    protected fun executeCommand(command: SupportedCommandType) {
        commandsMutableLiveData.value = command
    }

    // what is this model? What I tried to do in UIModel, but totally missed the point?
    @CallSuper // Denotes that any overriding methods should invoke this method as well.
    protected open fun refreshView() {
        modelUpdateEvent.value = model
    }
}