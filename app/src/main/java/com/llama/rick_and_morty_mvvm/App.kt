package com.llama.rick_and_morty_mvvm

import android.app.Application
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModelFactory

class App : Application() {

    lateinit var factory: HomeViewModelFactory

    override fun onCreate() {
        super.onCreate()

        url = getString(R.string.base_url)
        factory = HomeViewModelFactory(RepositoryImpl())
    }

    companion object {
        lateinit var url: String
            private set
    }

}