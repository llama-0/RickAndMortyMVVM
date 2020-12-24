package com.llama.rick_and_morty_mvvm.ui.command

import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand

sealed class DetailsCommand {
    class OpenLink(val url: String) : BaseCommand
}