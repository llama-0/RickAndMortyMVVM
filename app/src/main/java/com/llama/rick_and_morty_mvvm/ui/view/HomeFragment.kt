package com.llama.rick_and_morty_mvvm.ui.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.App
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_error_layout.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initRetryButton()
        refreshRecyclerViewData()
    }

    private fun initViewModel() {
        activity?.application.let {
            if (it is App) {
                viewModel = ViewModelProvider(this, it.factory).get(HomeViewModel::class.java)
            }
        }
    }

    private fun initRetryButton() {
        btn_retry.setOnClickListener {
            viewModel.retryBtn.observe(viewLifecycleOwner, {
                viewModel.snackbarMessage.observe(viewLifecycleOwner, {
                    showSnackbar(fragment_home_layout, getString(R.string.check_internet_connection_message))
                })
            })
        }
    }

    private fun refreshRecyclerViewData() {
        viewModel.dataList.observe(viewLifecycleOwner, { initAdapter(it) })
        viewModel.errorState.observe(viewLifecycleOwner, { recycler_error_layout.isVisible = it })
        viewModel.loadState.observe(viewLifecycleOwner, { progress_bar_layout.isVisible = it })
    }

    // через vm вызов снекбара сделать.
    private fun initAdapter(list: List<SimpleCharacter>) {
        rv_items.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv_items, character.name) // viewModel.onItemClicked() ?
        }
    }

    private fun showSnackbar(view: View, message: String) {
        Snackbar.make(
                view,
                message,
                Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val TAG = "TAG"
    }
}