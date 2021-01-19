package com.llama.rick_and_morty_mvvm.ui.command

import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand

sealed class CharactersCommand : BaseCommand {
    class ShowSnackbar(var message: String) : CharactersCommand()
    class OpenDetailsScreen(val characterId: Int) : CharactersCommand()
}