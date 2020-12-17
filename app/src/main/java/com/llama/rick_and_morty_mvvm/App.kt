package com.llama.rick_and_morty_mvvm

import android.app.Application
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.utils.HomeScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModelFactory

class App : Application() {

    private val apiService: ApiService by lazy { ApiServiceBuilder(url).buildService() }
    private val model: HomeScreenState by lazy {
        HomeScreenState(
            emptyList(),
            errorLayoutVisibility = false,
            progressBarVisibility = false
        )
    }
    val factory: HomeViewModelFactory by lazy {
        HomeViewModelFactory(
            RepositoryImpl(apiService),
            model
        )
    }

    override fun onCreate() {
        super.onCreate()

        url = getString(R.string.base_url)
    }

    companion object {
        lateinit var url: String
            private set
    }

}