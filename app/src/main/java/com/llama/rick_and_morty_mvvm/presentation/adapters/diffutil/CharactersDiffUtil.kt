package com.llama.rick_and_morty_mvvm.presentation.adapters.diffutil

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter

class CharactersDiffUtil : DiffUtil.ItemCallback<SimpleCharacter>() {
    override fun areItemsTheSame(oldItem: SimpleCharacter, newItem: SimpleCharacter): Boolean =
        oldItem.id == newItem.id // TODO: probably need to check all fields in general, but leave just `id` for now

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: SimpleCharacter, newItem: SimpleCharacter): Boolean =
        oldItem == newItem
}