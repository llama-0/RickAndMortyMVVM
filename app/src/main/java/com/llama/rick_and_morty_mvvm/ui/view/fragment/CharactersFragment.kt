package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseCommand
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.Navigate
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.view.CharactersAdapter
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModel

class CharactersFragment :
    BaseFragment<CharactersScreenState, BaseCommand, CharactersViewModel>(
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
        selectChips()
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
            tvChooseGender.isVisible = screenState.chipsGroupVisibility
        }
        setAdapter(screenState.dataList)
    }

    override fun executeCommand(command: BaseCommand) {
        when (command) {
            is ShowSnackbar -> showSnackbar(binding.root, command.message)
            is Navigate -> requireView().findNavController().navigate(
                command.destinationId
            )
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
            viewModel.onItemClicked(character.id)
        }
    }

    /**
     * function logic:
     *  1. Run through Chips in a ChipGroup;
     *  2. set checkedChangeListener on each Chip, in which
     *  3. call viewModel methods onChipChecked / onChipUnchecked
     * depending on isChecked Chip state
     * */
    private fun selectChips() {
        val chipGroup: ChipGroup = binding.chipGroupGender
        val list: MutableList<String> = mutableListOf()
        for (idx in 0 until chipGroup.childCount) {
            val chip: Chip = chipGroup.getChildAt(idx) as Chip

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    list.add(chip.text.toString())
                    viewModel.onChipChecked(list)
                } else {
                    list.remove(chip.text.toString())
                    viewModel.onChipUnchecked(list)
                }
            }
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "TAG"
    }
}