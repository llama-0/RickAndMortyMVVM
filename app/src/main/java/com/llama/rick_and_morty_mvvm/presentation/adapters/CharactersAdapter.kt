package com.llama.rick_and_morty_mvvm.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.llama.rick_and_morty_mvvm.databinding.ItemCharacterBinding
import com.llama.rick_and_morty_mvvm.domain.models.SimpleCharacter
import com.llama.rick_and_morty_mvvm.presentation.adapters.diffutil.CharactersDiffUtil
import com.llama.rick_and_morty_mvvm.presentation.adapters.viewhodlers.CharactersViewHolder

class CharactersAdapter(
    private val items: List<SimpleCharacter>,
    private val listener: (SimpleCharacter) -> Unit
) : ListAdapter<SimpleCharacter, CharactersViewHolder>(CharactersDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder =
        CharactersViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item: SimpleCharacter = items[position]
        bind(holder, item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    private fun bind(holder: CharactersViewHolder, model: SimpleCharacter) {
        holder.tvName.text = model.name
        holder.tvStatus.text = model.status
    }

    override fun getItemCount(): Int =
        items.size
}