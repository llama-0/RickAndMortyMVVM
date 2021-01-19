package com.llama.rick_and_morty_mvvm.ui.command

import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand

sealed class DetailsCommand : BaseCommand {
    class OpenLinkInWebView(val url: String) : DetailsCommand()
    object OpenLinkInBrowser : DetailsCommand()
}