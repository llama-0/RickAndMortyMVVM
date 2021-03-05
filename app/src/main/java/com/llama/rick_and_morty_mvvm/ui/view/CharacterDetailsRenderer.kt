package com.llama.rick_and_morty_mvvm.ui.view

import android.content.res.Resources
import androidx.core.text.HtmlCompat
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharacterDetailsBinding
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharacterDetailsViewModel

class CharacterDetailsRenderer(
    private val screenState: CharacterDetailsScreenState,
    private val viewModel: CharacterDetailsViewModel,
    private val binding: FragmentCharacterDetailsBinding,
    private val resources: Resources
) {

    fun render() {
        renderBasicFields()
        renderTextViewStatus()
        renderImageUrl()
    }

    private fun renderBasicFields() {
        with(binding) {
            tvName.text = screenState.name
            tvGender.text = screenState.gender
            tvStatus.text = screenState.status
            tvSpecies.text = screenState.species
            tvFirstSeenIn.text = screenState.firstSeenIn
            tvLastKnownLocation.text = screenState.lastSeenIn
        }
    }

    private fun renderTextViewStatus() {
        setImageViewStatus(binding.tvStatus.text.toString())
    }

    private fun renderImageUrl() {
        with(binding) {
            val imageUrlText: String = resources.getString(
                R.string.character_image_link,
                screenState.image,
                resources.getString(R.string.show_character_image_clickable_link_name)
            )
            tvImage.text = HtmlCompat.fromHtml(
                imageUrlText,
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            viewModel.onUrlClicked()
        }
    }

    private fun setImageViewStatus(status: String) {
        with(binding) {
            when (status) {
                resources.getString(R.string.status_alive_api_field) -> ivStatus.setDrawable(R.drawable.oval_status_alive)
                resources.getString(R.string.status_dead_api_field) -> ivStatus.setDrawable(R.drawable.oval_status_dead)
                else -> ivStatus.setDrawable(R.drawable.oval_status_unknown)
            }
        }
    }
}