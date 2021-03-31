package com.llama.rick_and_morty_mvvm.presentation.commands

import com.llama.rick_and_morty_mvvm.presentation.base.BaseCommand

sealed class DetailsCommand : BaseCommand {
    class OpenLinkInWebView(val url: String) : DetailsCommand()
    object OpenLinkInBrowser : DetailsCommand()
}