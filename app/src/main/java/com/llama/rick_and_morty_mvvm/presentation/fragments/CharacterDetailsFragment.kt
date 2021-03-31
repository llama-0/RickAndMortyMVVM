package com.llama.rick_and_morty_mvvm.presentation.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.presentation.base.BaseFragment
import com.llama.rick_and_morty_mvvm.presentation.commands.DetailsCommand
import com.llama.rick_and_morty_mvvm.presentation.commands.DetailsCommand.OpenLinkInBrowser
import com.llama.rick_and_morty_mvvm.presentation.commands.DetailsCommand.OpenLinkInWebView
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.presentation.viewmodels.CharacterDetailsViewModel


class CharacterDetailsFragment :
    BaseFragment<CharacterDetailsScreenState, DetailsCommand, CharacterDetailsViewModel>(
        R.layout.fragment_character_details,
        CharacterDetailsViewModel::class.java
    ) {

    private val binding: FragmentCharacterDetailsBinding by viewBinding(FragmentCharacterDetailsBinding::bind)

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCharacterFromSafeArgs()
        addCharacterToFavorites()
    }

    private fun getCharacterFromSafeArgs() {
        viewModel.getCharacter(args.characterId)
    }

    private fun addCharacterToFavorites() {
        binding.ivActionFavorite.setOnClickListener {
            viewModel.addCharacter()
        }
    }

    override fun renderView(screenState: CharacterDetailsScreenState) {
        CharacterDetailsRenderer(screenState, viewModel, binding, resources)
            .render()
    }

    override fun executeCommand(command: DetailsCommand) {
        with(binding) {
            when (command) {
                is OpenLinkInWebView -> tvImage.setOnClickListener { wvImage.loadUrl(command.url) }
                is OpenLinkInBrowser -> tvImage.movementMethod =
                    LinkMovementMethod.getInstance()
            }
        }
    }
}