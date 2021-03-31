package com.llama.rick_and_morty_mvvm.presentation.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.presentation.base.BaseFragment
import com.llama.rick_and_morty_mvvm.presentation.commands.CharactersCommand
import com.llama.rick_and_morty_mvvm.presentation.commands.CharactersCommand.*
import com.llama.rick_and_morty_mvvm.presentation.screenstates.CharactersScreenState
import com.llama.rick_and_morty_mvvm.presentation.viewmodels.CharactersViewModel

class CharactersFragment :
    BaseFragment<CharactersScreenState, CharactersCommand, CharactersViewModel>(
        R.layout.fragment_characters,
        CharactersViewModel::class.java
    ) {

    private val binding: FragmentCharactersBinding by viewBinding(FragmentCharactersBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        initAdapter()
        initRetryButton()
        selectChips()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.navigationFavorites -> {
                viewModel.onFavoriteClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    private fun initAdapter() {
        initAdapter(binding.rvItems)
    }

    private fun initRetryButton() {
        binding.includedErrorLayout.btnRetry.setOnClickListener {
            viewModel.onButtonRetryClicked()
        }
    }

    private fun selectChips() {
        with(binding) {
            val chipIdsList: List<Int> = chipGroupGender.children.toList().map { it.id }
            viewModel.mapChipIdsToGenderTypes(chipIdsList)
            chipGroupGender.children.forEach { chip ->
                chip.setOnClickListener {
                    viewModel.onChipClicked(it.id)
                }
            }
        }
    }

    override fun renderView(screenState: CharactersScreenState) {
        with(binding) {
            pbLoading.isVisible = screenState.progressBarVisibility
            includedErrorLayout.internetErrorLayout.isVisible =
                screenState.errorLayoutVisibility
            chipGroupGender.isVisible = screenState.chipsGroupVisibility
            tvChooseGender.isVisible = screenState.chipsGroupVisibility

            setAdapter(rvItems, screenState.dataList)
        }
    }

    override fun executeCommand(command: CharactersCommand) {
        when (command) {
            is ShowSnackbar -> {
                showSnackbar(binding.root, command.message)
            }

            is OpenDetailsScreen -> {
                val action: NavDirections = CharactersFragmentDirections
                    .actionNavigationCharactersToNavigationCharacterDetails(command.characterId)
                requireView().findNavController().navigate(action)
            }

            OpenFavoritesScreen -> {
                Navigation.findNavController(requireView()).navigate(R.id.navigationFavorites)
            }
        }
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "CharactersFragment"
    }
}