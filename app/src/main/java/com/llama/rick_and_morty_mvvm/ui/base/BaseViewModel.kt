package com.llama.rick_and_morty_mvvm.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.ui.utils.BaseScreenState
import com.llama.rick_and_morty_mvvm.ui.utils.Command
import com.llama.rick_and_morty_mvvm.ui.utils.SingleEventLiveData

abstract class BaseViewModel<
        ScreenState : BaseScreenState,
        SupportedCommandType : Command>(
    protected var model: ScreenState
) : ViewModel() {

    private val modelUpdateEvent = MutableLiveData<ScreenState>()
    private val commandsMutableLiveData: MutableLiveData<SupportedCommandType> =
        SingleEventLiveData()

    val modelUpdate: LiveData<ScreenState> = modelUpdateEvent
    val commandsLiveData: LiveData<SupportedCommandType> = commandsMutableLiveData

    protected fun executeCommand(command: SupportedCommandType) {
        commandsMutableLiveData.value = command
    }

    protected open fun refreshView() {
        modelUpdateEvent.value = model
    }
}