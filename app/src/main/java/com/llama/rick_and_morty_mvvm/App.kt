package com.llama.rick_and_morty_mvvm

import android.app.Application
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.view.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

class App : Application() {

    private lateinit var url: String

    private val apiService: ApiService by lazy { ApiServiceBuilder(url).buildService() }
    private val screenState: CharactersScreenState by lazy {
        CharactersScreenState(
            emptyList(),
            errorLayoutVisibility = false,
            progressBarVisibility = false,
            chipsGroupVisibility = false,
            isBtnRetryClicked = false,
            isGenderChipSelected = false
        )
    }
    private val defaultCharacter: SimpleCharacter by lazy {
        SimpleCharacter(
            -1,
            "",
            "",
            "",
            "",
            "",
            ""
        )
    }
    private val detailsScreenState: CharacterDetailsScreenState by lazy {
        CharacterDetailsScreenState(defaultCharacter)
    }
    val factory: CharactersViewModelFactory by lazy {
        CharactersViewModelFactory(
            RepositoryImpl(apiService),
            screenState,
            detailsScreenState,
            resources
        )
    }

    override fun onCreate() {
        super.onCreate()

        url = getString(R.string.base_url)
    }

}