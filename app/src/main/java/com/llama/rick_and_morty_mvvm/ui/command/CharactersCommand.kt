package com.llama.rick_and_morty_mvvm.ui.command

import android.os.Bundle
import androidx.annotation.IdRes

sealed class CharactersCommand : Command {
    class ShowSnackbar(var message: String) : Command
    class Navigate(@IdRes val destinationId: Int, val args: Bundle?) : Command
}