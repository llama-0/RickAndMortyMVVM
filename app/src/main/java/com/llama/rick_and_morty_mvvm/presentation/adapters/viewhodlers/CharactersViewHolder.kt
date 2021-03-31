package com.llama.rick_and_morty_mvvm.presentation.adapters.viewhodlers

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.databinding.ItemCharacterBinding


class CharactersViewHolder(binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

    val tvName: TextView = binding.tvName
    val tvStatus: TextView = binding.tvStatus
}