package com.llama.rick_and_morty_mvvm.ui.utils


sealed class Command
class ShowSnackbar(var message: String) : Command()

// for navigation between fragments which will appear later
//class Navigate(val destinationId: Int) : Command()