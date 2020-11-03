package com.llama.rick_and_morty_mvvm.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.model.Character

class HomeAdapter(
    private val items: List<Character>,
    private val listener: (Character) -> Unit
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    class HomeViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {

        private val id: TextView = itemsView.findViewById(R.id.tv_id)
        private val name: TextView = itemsView.findViewById(R.id.tv_name)

        fun bind(model: Character) {
            id.text = model.id.toString()
            name.text = model.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder =
        HomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false))

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item: Character = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            Log.d("adapter listener()", "onBindViewHolder: ${item.name}")
            listener(item)
        }
    }

    override fun getItemCount(): Int =
        items.size
}