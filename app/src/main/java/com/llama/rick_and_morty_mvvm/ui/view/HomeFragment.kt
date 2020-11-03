package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerView = view?.findViewById(R.id.rv_items)!!

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        retrieveCharacters()
    }

    private fun retrieveCharacters() {
        viewModel.getApiResponseListObserver().observe(viewLifecycleOwner, {
            if (it != null) {
                recyclerView.adapter = HomeAdapter(it) { character ->
                    Snackbar.make(
                            view?.findViewById<RecyclerView>(R.id.rv_items)!!,
                            character.name,
                            Snackbar.LENGTH_LONG
                    ).show()
                }
            } else {
                Log.d(this@HomeFragment.toString(), "retrieveCharacters: error occurred")
            }
        })
        viewModel.getCharacters()
    }
}