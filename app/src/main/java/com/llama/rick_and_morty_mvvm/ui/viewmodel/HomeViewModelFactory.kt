package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.ui.base.HomeScreenState
import com.llama.rick_and_morty_mvvm.ui.base.ShowSnackbar

class HomeViewModelFactory(
    private val repository: RepositoryImpl,
    private val model: HomeScreenState<*>,
    private val snackbarCommand: ShowSnackbar
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        HomeViewModel::class.java -> HomeViewModel(repository, model, snackbarCommand) as T
        else -> throw IllegalArgumentException("Unknown ViewModel class inside the factory create method")
    }
}