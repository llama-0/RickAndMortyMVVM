package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.CharacterDetailsRenderer
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharacterDetailsViewModel


class CharacterDetailsFragment :
    BaseFragment<CharacterDetailsScreenState, DetailsCommand, CharacterDetailsViewModel>(
        CharacterDetailsViewModel::class.java
    ) {

    private var binding: FragmentCharacterDetailsBinding? = null

    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCharacterDetailsBinding.bind(view)
        getCharacterFromSafeArgs()
    }

    private fun getCharacterFromSafeArgs() {
        viewModel.getCharacter(args.characterId)
    }

    override fun renderView(screenState: CharacterDetailsScreenState) {
        CharacterDetailsRenderer(screenState, viewModel, binding ?: return, resources)
            .render()
    }

    override fun executeCommand(command: DetailsCommand) {
        when (command) {
            is OpenLink -> {
                binding?.let {
                    with(it) {
                        tvImage.setOnClickListener {
                            wvImage.loadUrl(command.url)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}