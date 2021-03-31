package com.llama.rick_and_morty_mvvm.presentation.commands

import com.llama.rick_and_morty_mvvm.presentation.base.BaseCommand

sealed class CharactersCommand : BaseCommand {
    class ShowSnackbar(var message: String) : CharactersCommand()
    class OpenDetailsScreen(val characterId: Int) : CharactersCommand()
    object OpenFavoritesScreen : CharactersCommand()
}