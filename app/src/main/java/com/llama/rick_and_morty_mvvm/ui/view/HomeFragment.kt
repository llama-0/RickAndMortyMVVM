package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.HomeViewModelFactory
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModelFactory = HomeViewModelFactory(RepositoryImpl())
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        refreshRecyclerViewData()
    }

    private fun refreshRecyclerViewData() {
        viewModel.getCharacterListObserver().observe(viewLifecycleOwner, {
            Log.d(this@HomeFragment.toString(), "refreshRecyclerViewData: view model here or what?")
            if (it != null) {
                initAdapter(it)
            } else {
                Log.d(this@HomeFragment.toString(), "retrieveCharacters: error occurred")
            }
        })
    }

    private fun initAdapter(list: List<SimpleCharacter>) {
        rv_items.adapter = HomeAdapter(list) { character ->
            Snackbar.make(
                rv_items,
                character.name,
                Snackbar.LENGTH_LONG
            ).show()
        }
        Log.d(this@HomeFragment.toString(), "initAdapter: called")
    }
}