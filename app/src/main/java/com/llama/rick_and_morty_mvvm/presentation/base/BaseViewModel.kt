package com.llama.rick_and_morty_mvvm.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<
        ScreenState : BaseScreenState,
        CommandType : BaseCommand>(
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

    open fun onItemClicked(id: Int) {
    }
}