package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.text.Html
import android.text.Html.fromHtml
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharacterDetailsViewModel
import kotlin.math.absoluteValue


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

    @Suppress("unused")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onUrlClicked()
    }

    override fun renderView(screenState: CharacterDetailsScreenState) {
        with(binding) {
            tvName.text = screenState.character.name
            tvGender.text = screenState.character.gender
            tvStatus.text = screenState.character.status
            tvSpecies.text = screenState.character.species
            tvFirstSeenIn.text = screenState.character.firstSeenIn
            tvLastKnownLocation.text = screenState.character.lastSeenIn
            tvImage.movementMethod = LinkMovementMethod.getInstance()
            val text =
                "<a href='${screenState.character.image}'> ${getString(R.string.show_character_image)} </a>" // todo: whole string should be in resources
            // (but is I do so href functionality disappears)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                tvImage.text = fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
            } else {
                tvImage.text = fromHtml(text)
            }
            setImageViewStatus(tvStatus.text as String)
        }
    }

    // todo: remove code duplication; logic in fragment
    private fun setImageViewStatus(status: String) = with(binding) {
        when (status) {
            "Alive" -> ivStatus.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.oval_status_alive
                )
            )
            "Dead" -> ivStatus.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.oval_status_dead
                )
            )
            else -> ivStatus.setImageDrawable(
                getDrawable(
                    requireContext(),
                    R.drawable.oval_status_unknown
                )
            )
        }
    }

    @Suppress("unused")
    override fun executeCommand(command: BaseCommand) {
        when (command) {
            is OpenLink -> {
                with(binding) {
                    tvImage.setOnClickListener {
                        wvImage.loadUrl(command.url) // такой вариант мне больше нравится
                    }
                }
            }
        }
    }

    @Suppress("unused")
    private fun onUrlClicked() {
        val url: TextView = binding.tvImage
        url.setOnClickListener {
            viewModel.onUrlClicked()
        }
    }

    @Suppress("unused")
    companion object {
        private const val INT_CHARACTER_ID_KEY = "INT_CHARACTER_ID_KEY"
    }
}