package com.llama.rick_and_morty_mvvm.ui.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData

abstract class BaseViewModel<
        ScreenState : RefreshableScreenState<*>,
        SupportedCommandType : Command>(
    var model: ScreenState
) : ViewModel() {

    private val modelUpdateEvent = MutableLiveData<ScreenState>()
    private val commandsMutableLiveData: MutableLiveData<SupportedCommandType> =
        SingleEventLiveData()

    val modelUpdate: LiveData<ScreenState> = modelUpdateEvent
    val commandsLiveData: LiveData<SupportedCommandType> = commandsMutableLiveData

    // protected may cause "Kotlin: Cannot access 'executeCommand': it is protected"
    // in child classes
    protected fun executeCommand(command: SupportedCommandType) {
        commandsMutableLiveData.value = command
    }

    @CallSuper
    /* Denotes that any overriding methods should invoke this method as well. */
    protected open fun refreshView() {
        modelUpdateEvent.value = model
    }
}