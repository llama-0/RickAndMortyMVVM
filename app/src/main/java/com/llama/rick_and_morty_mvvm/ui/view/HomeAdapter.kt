package com.llama.rick_and_morty_mvvm.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.databinding.ListItemBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class HomeAdapter(
    private val items: List<SimpleCharacter>,
    private val listener: (SimpleCharacter) -> Unit
) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item: SimpleCharacter = items[position]
        bind(holder, item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    private fun bind(holder: HomeViewHolder, model: SimpleCharacter) {
        holder.id.text = model.id.toString()
        holder.name.text = model.name
    }

    override fun getItemCount(): Int =
        items.size
}