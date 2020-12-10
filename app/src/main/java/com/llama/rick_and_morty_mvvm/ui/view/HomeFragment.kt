package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.App
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentHomeBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, true)
        return binding.root
    }

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
        binding.includedErrorLayout.btnRetry.setOnClickListener {
            Log.d(TAG, "initRetryButton: on Click")
            viewModel.onButtonRetryClicked()
            subscribeToSnackbarObservable()
        }
    }

    private fun subscribeToSnackbarObservable() {
        viewModel.snackbarMessage.observe(viewLifecycleOwner) {
            if (it == true) {
                showSnackbar(binding.fragmentHomeLayout, getString(R.string.check_internet_connection_message))
            }
        }
    }

    private fun subscribeToViewModelObservables() {
        viewModel.loadState.observe(viewLifecycleOwner) { binding.includedLoadingLayout.progressBarLayout.isVisible = it }
        viewModel.errorState.observe(viewLifecycleOwner) { binding.includedErrorLayout.internetErrorLayout.isVisible = it }
        viewModel.dataList.observe(viewLifecycleOwner) { setAdapter(it) }
    }

    // через vm вызов снекбара сделать.
    private fun setAdapter(list: List<SimpleCharacter>) {
        val rv: RecyclerView = binding.rvItems
        rv.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv, character.name) // viewModel.onItemClicked() ?
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