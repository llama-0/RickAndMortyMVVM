package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.model.SimpleCharacter

class HomeViewModel(repository: RepositoryImpl) : ViewModel() {

    private val liveDataList: LiveData<List<SimpleCharacter>> = repository.getCharacters()

    fun getCharacterListObserver(): LiveData<List<SimpleCharacter>> =
            liveDataList
}