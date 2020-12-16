package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentHomeBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.utils.Command
import com.llama.rick_and_morty_mvvm.ui.utils.HomeScreenState
import com.llama.rick_and_morty_mvvm.ui.utils.ShowSnackbar
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

        initAdapter()
        initRetryButton()
    }

    private fun initAdapter() {
        val list: List<SimpleCharacter>? = emptyList()
        list?.let { setAdapter(it) }
    }

    override fun renderView(model: HomeScreenState<*>) {
        binding.includedLoadingLayout.progressBarLayout.isVisible =
            model.progressBarVisibility
        binding.includedErrorLayout.internetErrorLayout.isVisible =
            model.errorLayoutVisibility
        setAdapter(model.dataList)
    }

    override fun executeCommand(command: Command) {
        if (command is ShowSnackbar) {
            showSnackbar(binding.root, command.message)
        }
    }

    private fun initRetryButton() {
        binding.includedErrorLayout.btnRetry.setOnClickListener {
            viewModel.onButtonRetryClicked(getString(R.string.check_internet_connection_message))
        }
    }

    private fun setAdapter(list: List<SimpleCharacter>) {
        val rv: RecyclerView = binding.rvItems
        rv.adapter = HomeAdapter(list) { character ->
            viewModel.onItemClicked(character.name)
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
        @Suppress("unused")
        private const val TAG = "TAG"
    }
}