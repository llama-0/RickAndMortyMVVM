package com.llama.rick_and_morty_mvvm

import android.app.Application
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.HomeScreenState
import com.llama.rick_and_morty_mvvm.ui.model.UIModel
import com.llama.rick_and_morty_mvvm.ui.viewmodel.HomeViewModelFactory

class App : Application() {

    lateinit var factory: HomeViewModelFactory
    lateinit var apiService: ApiService
    lateinit var model: HomeScreenState<*>

    override fun onCreate() {
        super.onCreate()

        url = getString(R.string.base_url)
        apiService = ApiServiceBuilder(url).buildService()
        model = HomeScreenState<List<SimpleCharacter>>(emptyList(), UIModel())
        factory = HomeViewModelFactory(RepositoryImpl(apiService), model)
    }

    companion object {
        lateinit var url: String
            private set
    }

}