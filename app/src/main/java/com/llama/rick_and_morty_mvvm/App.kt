package com.llama.rick_and_morty_mvvm

import android.app.Application
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModelFactory

class App : Application() {
    val factory = HomeViewModelFactory(RepositoryImpl())
}