package com.llama.rick_and_morty_mvvm.ui.command

import androidx.annotation.IdRes

sealed class CharactersCommand : Command {
    class ShowSnackbar(var message: String) : Command
    class Navigate(@IdRes val destinationId: Int) : Command
}