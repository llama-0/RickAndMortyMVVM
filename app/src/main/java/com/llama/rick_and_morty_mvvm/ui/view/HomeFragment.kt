package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
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
        viewModel.updateUI().observe(viewLifecycleOwner, {
            initAdapter(it)
        })
        viewModel.updateErrorState().observe(viewLifecycleOwner, {
            if (it) {
                showErrorLayout()
            }
        })
    }

    private fun initAdapter(list: List<SimpleCharacter>) {
        setUpNormalView()
        rv_items.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv_items, character.name)
        }
    }

    private fun showErrorLayout() {
        Log.d("TAG", "showErrorLayout: inside")
        setUpErrorView()
        btn_retry.setOnClickListener {
            progress_bar_layout.visibility = View.VISIBLE
            viewModel.onButtonClicked()
                    .observe(viewLifecycleOwner, {
                showSnackbar(fragment_home_layout, getString(R.string.check_internet_connection_message))
            })
            viewModel.onButtonClickedDone()
            viewModel.updateUI().observe(viewLifecycleOwner, { initAdapter(it) })
            // how to intercept connection timeout ?
        }
    }

    private fun setUpNormalView() {
        recycler_error_layout.visibility = View.GONE
        progress_bar_layout.visibility = View.GONE
        rv_items.visibility = View.VISIBLE
    }

    private fun setUpErrorView() {
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