package com.llama.rick_and_morty_mvvm.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.ui.command.Command

abstract class BaseViewModel<
        ScreenState : BaseScreenState,
        CommandType : Command>(
    protected var screenState: ScreenState
) : ViewModel() {

    private val screenStateUpdateEvent = MutableLiveData<ScreenState>()
    private val commandsMutableLiveData: MutableLiveData<CommandType> =
        SingleEventLiveData()

    val screenStateLiveData: LiveData<ScreenState> = screenStateUpdateEvent
    val commandsLiveData: LiveData<CommandType> = commandsMutableLiveData

    protected fun executeCommand(command: CommandType) {
        commandsMutableLiveData.value = command
    }

    protected open fun refreshView() {
        screenStateUpdateEvent.value = screenState
    }
}