package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.Command
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharacterDetailsViewModel

class CharacterDetailsFragment :
    BaseFragment<CharacterDetailsScreenState, Command, CharacterDetailsViewModel>(
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

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.title = getString(R.string.title_character_details)

        val name = arguments?.getString("OBJ_CHARACTER_KEY") ?: "al"
        binding.tvName.text = name
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

    override fun executeCommand(command: Command) {
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

}