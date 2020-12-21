package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.Command
import com.llama.rick_and_morty_mvvm.ui.command.Command.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModel

class CharactersFragment : BaseFragment<CharactersScreenState, Command, CharactersViewModel>(
    CharactersViewModel::class.java
) {
    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initRetryButton()
    }

    private fun initAdapter() {
        val list: List<SimpleCharacter> = emptyList()
        setAdapter(list)
    }

    override fun renderView(screenState: CharactersScreenState) {
        with(binding) {
            pbLoading.isVisible = screenState.progressBarVisibility
            includedErrorLayout.internetErrorLayout.isVisible = screenState.errorLayoutVisibility
            chipGroupGender.isVisible = screenState.chipsGroupVisibility
        }
        setAdapter(screenState.dataList)
    }

    override fun executeCommand(command: Command) {
        if (command is ShowSnackbar) {
            showSnackbar(binding.root, command.message)
        }
    }

    private fun initRetryButton() {
        binding.includedErrorLayout.btnRetry.setOnClickListener {
            viewModel.onButtonRetryClicked()
        }
    }

    private fun setAdapter(list: List<SimpleCharacter>) {
        val rv: RecyclerView = binding.rvItems
        rv.adapter = CharactersAdapter(list) { character ->
            viewModel.onItemClicked(character.name)
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "TAG"
    }
}