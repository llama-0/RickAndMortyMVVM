package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
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

        Log.d(TAG, "onViewCreated: view created")
        initViewModel()
        initAdapter()
        subscribeToViewModelObservables()
        initRetryButton()
    }

    private fun initViewModel() {
        activity?.application.let {
            if (it is App) {
                viewModel = ViewModelProvider(this, it.factory).get(HomeViewModel::class.java)
            }
        }
    }

    private fun initAdapter() {
        val list: List<SimpleCharacter>? = emptyList()
        list?.let { setAdapter(it) }
    }

    private fun initRetryButton() {
        btn_retry.setOnClickListener {
            Log.d(TAG, "initRetryButton: on Click")
            viewModel.onButtonRetryClicked()
            subscribeToSnackbarObservable()
        }
    }

    private fun subscribeToSnackbarObservable() {
        viewModel.snackbarMessage.observe(viewLifecycleOwner, {
            if (it == true) { // todo: move this logic to VM
                showSnackbar(fragment_home_layout, getString(R.string.check_internet_connection_message))
            }
        })
    }

    private fun subscribeToViewModelObservables() {
        viewModel.loadState.observe(viewLifecycleOwner) { progress_bar_layout.isVisible = it }
        viewModel.errorState.observe(viewLifecycleOwner) { recycler_error_layout.isVisible = it }
        viewModel.dataList.observe(viewLifecycleOwner) { setAdapter(it) }
    }

    // через vm вызов снекбара сделать.
    private fun setAdapter(list: List<SimpleCharacter>) {
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