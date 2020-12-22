package com.llama.rick_and_morty_mvvm.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.Navigate
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.command.Command
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModel

class CharactersFragment :
    BaseFragment<CharactersScreenState, Command, CharactersViewModel>(
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

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.title = getString(R.string.title_characters)

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
            chipFemale.isChecked = screenState.isFemaleChipSelected
        }
        setAdapter(screenState.dataList)
    }

    override fun executeCommand(command: Command) {
        when (command) {
            is ShowSnackbar -> showSnackbar(binding.root, command.message)
            is Navigate -> requireView().findNavController().navigate(
                command.destinationId,
                command.args
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
            viewModel.onItemClicked(character)
        }
    }

//    private fun selectChips() {
//        val chipGroup = binding.chipGroupGender
//        for (idx in 0 until chipGroup.childCount) {
//            val chip = chipGroup.getChildAt(idx) as Chip
//
//            chip.setOnCheckedChangeListener { _, isChecked ->
//                if (isChecked) {
//                    viewModel.onChipChecked(chip.text.toString())
//                } else {
//                    viewModel.onChipUnchecked(chip.text.toString())
//                }
//            }
//        }
//    }

    companion object {
        @Suppress("unused")
        private const val TAG = "TAG"
    }
}