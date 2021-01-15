package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.llama.rick_and_morty_mvvm.BuildConfig
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand
import com.llama.rick_and_morty_mvvm.ui.command.DetailsCommand.OpenLink
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.setDrawable
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharacterDetailsViewModel


class CharacterDetailsFragment :
    BaseFragment<CharacterDetailsScreenState, DetailsCommand, CharacterDetailsViewModel>(
        CharacterDetailsViewModel::class.java
    ) {

    private var binding: FragmentCharacterDetailsBinding? = null

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
    }

    override fun renderView(screenState: CharacterDetailsScreenState) {
        binding?.let {
            with(it) {
                tvName.text = screenState.name
                tvGender.text = screenState.gender
                tvStatus.text = screenState.status
                setImageViewStatus(tvStatus.text.toString())
                tvSpecies.text = screenState.species
                tvFirstSeenIn.text = screenState.firstSeenIn
                tvLastKnownLocation.text = screenState.lastSeenIn
                val imageUrlText: String = getString(
                    R.string.character_image_link,
                    screenState.image,
                    getString(R.string.show_character_image_clickable_link_name)
                )
                tvImage.text = HtmlCompat.fromHtml(imageUrlText, HtmlCompat.FROM_HTML_MODE_LEGACY)
                if (BuildConfig.IS_WEB_VIEW_FEATURE_ON) {
                    viewModel.onUrlClicked()
                } else {
                    tvImage.movementMethod = LinkMovementMethod.getInstance()
                }
            }
        }
    }

    private fun setImageViewStatus(status: String) {
        binding?.let {
            with(it) {
                when (status) {
                    STR_STATUS_ALIVE -> ivStatus.setDrawable(R.drawable.oval_status_alive)
                    STR_STATUS_DEAD -> ivStatus.setDrawable(R.drawable.oval_status_dead)
                    else -> ivStatus.setDrawable(R.drawable.oval_status_unknown)
                }
            }
        }
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

    companion object {
        private const val STR_STATUS_ALIVE = "Alive"
        private const val STR_STATUS_DEAD = "Dead"
    }
}