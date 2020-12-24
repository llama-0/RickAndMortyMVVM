package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharacterDetailsViewModel

class CharacterDetailsFragment :
    BaseFragment<CharacterDetailsScreenState, BaseCommand, CharacterDetailsViewModel>(
        CharacterDetailsViewModel::class.java
    ) {

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onUrlClicked()
    }

    override fun renderView(screenState: CharacterDetailsScreenState) {
        with(binding) {
            tvName.text = screenState.character.name
            tvGender.text = screenState.character.gender
            tvStatus.text = screenState.character.status
            tvFrom.text = screenState.character.from
            tvLastSeenIn.text = screenState.character.lastSeenIn
            tvImage.text = screenState.character.image
        }
    }

    override fun executeCommand(command: BaseCommand) {
        when (command) {
            is OpenLink -> WebViewClient().onLoadResource(
                requireView() as WebView?,
                command.url
            )
        }
    }

    private fun onUrlClicked() {
        val url = binding.tvImage
        url.setOnClickListener {
            viewModel.onUrlClicked()
        }
    }

    companion object {
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }
}