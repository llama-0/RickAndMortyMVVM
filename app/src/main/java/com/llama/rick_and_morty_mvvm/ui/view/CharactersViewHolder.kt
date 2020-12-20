package com.llama.rick_and_morty_mvvm.ui.view

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.databinding.ListItemBinding


class CharactersViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val tvId: TextView = binding.tvId
    val tvName: TextView = binding.tvName
}