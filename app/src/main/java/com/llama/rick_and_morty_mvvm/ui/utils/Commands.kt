package com.llama.rick_and_morty_mvvm.ui.utils


sealed class Command
class ShowSnackbar(var message: String) : Command()

//object ClickButton : Command()
//class Navigate(val destinationId: Int) : Command()
//object SwipeRefresh : Command()