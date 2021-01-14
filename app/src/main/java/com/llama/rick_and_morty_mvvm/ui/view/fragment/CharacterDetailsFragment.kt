package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.text.HtmlCompat
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
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
            tvSpecies.text = screenState.character.species
            tvFirstSeenIn.text = screenState.character.firstSeenIn
            tvLastKnownLocation.text = screenState.character.lastSeenIn
            tvImage.movementMethod = LinkMovementMethod.getInstance()
            val imageUrlText: String = getString(
                R.string.character_image_link,
                screenState.character.image,
                getString(R.string.show_character_image_clickable_link_name)
            )
            tvImage.text = HtmlCompat.fromHtml(imageUrlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
            setImageViewStatus(tvStatus.text as String)
        }
    }

    private fun setImageViewStatus(status: String): Unit = with(binding) {
        when (status) {
            STR_STATUS_ALIVE -> ivStatus.setDrawable(R.drawable.oval_status_alive)
            STR_STATUS_DEAD -> ivStatus.setDrawable(R.drawable.oval_status_dead)
            else -> ivStatus.setDrawable(R.drawable.oval_status_unknown)
        }
    }

    private fun ImageView.setDrawable(id: Int) {
        setImageDrawable(
            getDrawable(
                requireContext(),
                id
            )
        )
    }

    @Suppress("unused")
    override fun executeCommand(command: BaseCommand) {
        when (command) {
            is OpenLink -> {
                with(binding) {
                    tvImage.setOnClickListener {
                        wvImage.loadUrl(command.url)
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

    companion object {
        private const val STR_STATUS_ALIVE = "Alive"
        private const val STR_STATUS_DEAD = "Dead"
    }
}