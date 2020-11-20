package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.App
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModelFactory
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_error_layout.*

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var viewModel: HomeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // проверить, что в лайв дате что-то есть
                // в HomeViewModel в поле сохранить данные списка. разобраться, почему лайв дата может очищаться
        // локальный кеш сделать и оттуда брать список

        Log.d(TAG, "onViewCreated: view created")
        initViewModel()
        refreshRecyclerViewData()
        Log.d(TAG, "onViewCreated: data ================== ${viewModel.dataList.value}")
        initRetryButton()
    }

    private fun initViewModel() {
        activity?.application.let {
            if (it is App) {
                viewModel = ViewModelProvider(this, it.factory).get(HomeViewModel::class.java)
            }
        }
    }

    private fun initRetryButton() {
        Log.d(TAG, "initRetryButton: init")
        btn_retry.setOnClickListener {
            Log.d(TAG, "initRetryButton: on Click")
            viewModel.retryBtn
//                    .observe(viewLifecycleOwner, {
//                Log.d(TAG, "initRetryButton: called -------------------- called retryBtn")
//            })
            viewModel.snackbarMessage.observe(viewLifecycleOwner, {
                it.getContentIfNotHandled()?.let {
                    Log.d(TAG, "initRetryButton: called -------------------- called snackbarMessage")
                    showSnackbar(fragment_home_layout, getString(R.string.check_internet_connection_message))
                }
            })
        }
    }

    private fun refreshRecyclerViewData() {
        Log.d(TAG, "refreshRecyclerViewData: dataList, errorState, loadState")
        viewModel.loadState.observe(viewLifecycleOwner, {
            Log.d(TAG, "refreshRecyclerViewData: loadState")
            progress_bar_layout.isVisible = it
        })
        viewModel.errorState.observe(viewLifecycleOwner, {
            Log.d(TAG, "refreshRecyclerViewData: errorState")
            recycler_error_layout.isVisible = it
        })
        viewModel.dataList.observe(viewLifecycleOwner, {
            Log.d(TAG, "refreshRecyclerViewData: dataList")
            initAdapter(it)
        })
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