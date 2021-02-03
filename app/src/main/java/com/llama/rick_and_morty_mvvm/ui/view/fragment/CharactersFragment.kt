package com.llama.rick_and_morty_mvvm.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.llama.rick_and_morty_mvvm.databinding.FragmentCharactersBinding
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseFragment
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.OpenDetailsScreen
import com.llama.rick_and_morty_mvvm.ui.command.CharactersCommand.ShowSnackbar
import com.llama.rick_and_morty_mvvm.ui.model.GenderType.*
import com.llama.rick_and_morty_mvvm.ui.view.CharactersAdapter
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
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding?.root
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

    private fun initRetryButton() {
        binding?.includedErrorLayout?.btnRetry?.setOnClickListener {
            viewModel.onButtonRetryClicked()
        }
    }

    // kinda hardcode ...
    private fun selectChips() {
        with(binding ?: return) {
            chipFemale.setOnClickListener {
                viewModel.onChipClicked(FEMALE)
            }
            chipMale.setOnClickListener {
                viewModel.onChipClicked(MALE)
            }
            chipGenderless.setOnClickListener {
                viewModel.onChipClicked(GENDERLESS)
            }
            chipUnknown.setOnClickListener {
                viewModel.onChipClicked(UNKNOWN)
            }
        }
    }

    private fun setAdapter(list: List<SimpleCharacter>) {
        val rv: RecyclerView = binding?.rvItems ?: return
        rv.adapter = CharactersAdapter(list) { character ->
            viewModel.onItemClicked(character.id)
        }
    }

    override fun renderView(screenState: CharactersScreenState) {
        with(binding ?: return) {
            pbLoading.isVisible = screenState.progressBarVisibility
            includedErrorLayout.internetErrorLayout.isVisible =
                screenState.errorLayoutVisibility
            chipGroupGender.isVisible = screenState.chipsGroupVisibility
            tvChooseGender.isVisible = screenState.chipsGroupVisibility
        }
        setAdapter(screenState.dataList)
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