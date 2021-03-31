package com.llama.rick_and_morty_mvvm.presentation.commands

import com.llama.rick_and_morty_mvvm.presentation.base.BaseCommand

sealed class FavoritesCommand : BaseCommand {
    class OpenDetailsScreen(val characterId: Int) : FavoritesCommand()
}