package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.App
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recycler_error_layout.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewModel()
        refreshRecyclerViewData()
    }

    private fun initViewModel() {
        activity?.application.let {
            if (it is App) {
                viewModel = ViewModelProvider(this, it.factory).get(HomeViewModel::class.java)
            }
        }
    }

    private fun refreshRecyclerViewData() {
        viewModel.errorState.observe(viewLifecycleOwner, {
            showErrorLayout()
        })
        viewModel.updateUI().observe(viewLifecycleOwner, {
            initAdapter(it)
        })
    }

    private fun initAdapter(list: List<SimpleCharacter>) {
        Log.d(this@HomeFragment.toString(), "initAdapter: called")
        recycler_error_layout.visibility = View.GONE
        rv_items.visibility = View.VISIBLE
        rv_items.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv_items, character.name)
        }
    }

    private fun showErrorLayout() {
        Log.d(this@HomeFragment.toString(), "showErrorScreen: error")
        recycler_error_layout.visibility = View.VISIBLE
        rv_items.visibility = View.GONE
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
        ).show()
    }
}