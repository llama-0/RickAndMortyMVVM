package com.llama.rick_and_morty_mvvm.ui.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.R


class HomeViewHolder(itemsView: View) : RecyclerView.ViewHolder(itemsView) {

    val id: TextView = itemsView.findViewById(R.id.tv_id)
    val name: TextView = itemsView.findViewById(R.id.tv_name)
}