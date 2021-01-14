package com.llama.rick_and_morty_mvvm.ui.command

import androidx.annotation.IdRes
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand

sealed class CharactersCommand : BaseCommand {
    class ShowSnackbar(var message: String) : CharactersCommand()
    class Navigate(@IdRes val destinationId: Int) : CharactersCommand()
}