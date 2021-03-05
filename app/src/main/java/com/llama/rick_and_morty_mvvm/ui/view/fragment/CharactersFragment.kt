package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.view.*
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.*
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModel

class CharactersFragment :
    BaseFragment<CharactersScreenState, CharactersCommand, CharactersViewModel>(
        CharactersViewModel::class.java
    ) {

    private var binding: FragmentCharactersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        binding?.let {
            initAdapter(it.rvItems)
        }
    }

    private fun initRetryButton() {
        binding?.includedErrorLayout?.btnRetry?.setOnClickListener {
            viewModel.onButtonRetryClicked()
        }
    }

    private fun selectChips() {
        with(binding ?: return) {
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
        with(binding ?: return) {
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
                showSnackbar(binding?.root ?: return, command.message)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "CharactersFragment"
    }
}