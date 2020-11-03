package com.llama.rick_and_morty_mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.model.Character

class HomeViewModel(private val repository: RepositoryImpl) : ViewModel() {

    private var liveDataList: LiveData<List<Character>> = MutableLiveData() // migrate to LiveData and repository

    fun getCharacterListObserver(): LiveData<List<Character>> =
            liveDataList

    fun getCharactersFromRepository() {
        liveDataList = repository.getCharacters()
    }
}