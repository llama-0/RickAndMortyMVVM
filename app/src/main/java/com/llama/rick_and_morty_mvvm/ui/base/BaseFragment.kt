package com.llama.rick_and_morty_mvvm.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData

abstract class BaseFragment<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command,
        ViewModel : BaseViewModel<ScreenState, SupportedCommandType>> ( // why name the BaseViewModel Refreshable ?
    @LayoutRes val layoutResId: Int,
            viewModelClass: Class<ViewModel>
) : Fragment(layoutResId) {

    protected open val viewModel: ViewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(viewModelClass)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        subscribeToViewModelObservables()
    }

    private fun subscribeToViewModelObservables() {
        val modelObserver = Observer(this::renderView)
        viewModel.modelUpdate.observe(viewLifecycleOwner, modelObserver)
        val commandObserver = Observer(this::executeCommand)
        viewModel.commandsLiveData.observe(viewLifecycleOwner, commandObserver)

    }

    protected abstract fun renderView(model: ScreenState)

    protected open fun executeCommand(command: SupportedCommandType) {
//        todo:
//        showUnderConstructionMessage()
    }
}

/*
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

abstract class BaseViewModel<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command>(
        val model: ScreenState
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

    @CallSuper /* Denotes that any overriding methods should invoke this method as well. */
    protected open fun refreshView() {
        modelUpdateEvent.value = model
    }
}