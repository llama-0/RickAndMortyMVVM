package com.llama.rick_and_morty_mvvm

import android.app.Application
import com.llama.rick_and_morty_mvvm.presentation.models.ViewModelFactoryCreator
import com.llama.rick_and_morty_mvvm.presentation.viewmodels.CharactersViewModelFactory

class App : Application() {

    val factory: CharactersViewModelFactory by lazy {
        ViewModelFactoryCreator().create(resources)
    }
}