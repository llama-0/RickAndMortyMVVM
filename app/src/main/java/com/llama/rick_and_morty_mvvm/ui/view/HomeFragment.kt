package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_error_layout.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        refreshRecyclerViewData()
    }

    private fun initViewModel() {
        val viewModelFactory = HomeViewModelFactory(RepositoryImpl())
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun refreshRecyclerViewData() {
        viewModel.getLiveDataList().observe(viewLifecycleOwner, {
            when {
                it.isNotEmpty() -> initAdapter(it)
                it.isEmpty() -> showErrorLayout()
            }
        })
    }

    private fun initAdapter(list: List<SimpleCharacter>) {
        Log.d(this@HomeFragment.toString(), "initAdapter: called")
        progress_bar_layout.visibility = View.GONE
        recycler_error_layout.visibility = View.GONE
        rv_items.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv_items, character.name)
        }
    }

    private fun showErrorLayout() {
        Log.d(this@HomeFragment.toString(), "showErrorScreen: error")
        recycler_error_layout.visibility = View.VISIBLE
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
        ).show()
    }
}