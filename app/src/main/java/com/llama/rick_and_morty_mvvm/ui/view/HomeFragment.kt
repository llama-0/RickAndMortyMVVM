package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentHomeBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.*
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeScreenState<*>, Command, HomeViewModel>(
    HomeViewModel::class.java
) {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d(TAG, "onViewCreated: view created")
        initAdapter()
        initRetryButton()
    }

    private fun initAdapter() {
        val list: List<SimpleCharacter>? = emptyList()
        list?.let { setAdapter(it) }
    }

    override fun renderView(model: HomeScreenState<*>) {
        binding.includedLoadingLayout.progressBarLayout.isVisible =
            model.progressBarVisibility.value ?: return
        binding.includedErrorLayout.internetErrorLayout.isVisible =
            model.errorLayoutVisibility.value ?: return
        setAdapter(model.dataList.value ?: return)
    }

    override fun executeCommand(command: Command) {
        if (command is ShowSnackbar) {// && viewModel.model.isSnackbarActionRequired.value == true) { again the problem with double appearance of msg
            Log.e("TAG", "executeCommand: inside HomeFragment")
            showSnackbar(binding.root, getString(R.string.check_internet_connection_message))
        }
    }

    private fun initRetryButton() {
        binding.includedErrorLayout.btnRetry.setOnClickListener {
            Log.d(TAG, "initRetryButton: on Click")
            viewModel.onButtonRetryClicked()
        }
    }

//    private fun subscribeToSnackbarObservable() {
//        viewModel.snackbarMessage.observe(viewLifecycleOwner) {
//            if (it == true) {
//                showSnackbar(
//                    binding.fragmentHomeLayout,
//                    getString(R.string.check_internet_connection_message)
//                )
//            }
//        }
//    }

    // через vm вызов снекбара сделать.
    private fun setAdapter(list: List<SimpleCharacter>) {
        val rv: RecyclerView = binding.rvItems
        Log.d(TAG, "setAdapter: inside adapter")
        rv.adapter = HomeAdapter(list) { character ->
            showSnackbar(rv, character.name) // viewModel.onItemClicked() ?
        }
        Log.d(TAG, "setAdapter: list = $list inside adapter")
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