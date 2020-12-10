package com.llama.rick_and_morty_mvvm.ui.view

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.databinding.ListItemBinding


class HomeViewHolder(binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    val id: TextView = binding.tvId
    val name: TextView = binding.tvName
}