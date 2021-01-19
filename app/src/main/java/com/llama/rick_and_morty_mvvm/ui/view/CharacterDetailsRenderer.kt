package com.llama.rick_and_morty_mvvm.ui.view

import android.content.res.Resources
import android.text.method.LinkMovementMethod
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
            viewModel.toggleWebViewFeature()
//                tvImage.movementMethod = LinkMovementMethod.getInstance() // commented because toggle won't work other way. What should I do?..
        }
    }

    // move logic to viewmodel: ScreenState(+ isAlive, isDead, isUnknown <- but in compact way). One Boolean like `isAlive` is not enough, decoding by -1, 0, 1 is unclear
    private fun setImageViewStatus(status: String) {
        with(binding) {
            when (status) {
                STR_STATUS_ALIVE -> ivStatus.setDrawable(R.drawable.oval_status_alive)
                STR_STATUS_DEAD -> ivStatus.setDrawable(R.drawable.oval_status_dead)
                else -> ivStatus.setDrawable(R.drawable.oval_status_unknown)
            }
        }
    }

    // call it in when(status) { Status.ALIVE.value -> ...}
//    inner enum class Status(val value: String) {
//        ALIVE(resources.getString(R.string.female_gender_api_field)),
//        DEAD("Dead"),
//        UNKNOWN("unknown")
//    }

    companion object {
        private const val STR_STATUS_ALIVE = "Alive"
        private const val STR_STATUS_DEAD = "Dead"
    }
}