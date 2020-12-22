package com.llama.rick_and_morty_mvvm.ui.command

sealed class DetailsCommand {
    class OpenLink(val url: String) : Command
}