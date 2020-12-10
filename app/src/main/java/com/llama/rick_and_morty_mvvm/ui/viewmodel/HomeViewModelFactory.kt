package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl

class HomeViewModelFactory(
        private val repository: RepositoryImpl
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            modelClass.getConstructor(RepositoryImpl::class.java)
                    .newInstance(repository)
}