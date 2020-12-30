package com.llama.rick_and_morty_mvvm

import android.app.Application
import android.content.SharedPreferences
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

class App : Application() {

    private lateinit var url: String
    private val sharedPrefs: SharedPreferences by lazy {
        getSharedPreferences(STR_APPS_SHARED_PREFS_FILE_NAME, MODE_PRIVATE)
    }

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
            "name",
            "Male",
            "Alive",
            "Human",
            "image is here",
            "from",
            "to"
        )
    }
    private val detailsScreenState: CharacterDetailsScreenState by lazy {
        CharacterDetailsScreenState(defaultCharacter)
    }
    private val repository: RepositoryImpl by lazy {
        RepositoryImpl(apiService)
    }
    private val interactor: CharactersInteractor by lazy {
        CharactersInteractor(repository)
    }
    val factory: CharactersViewModelFactory by lazy {
        CharactersViewModelFactory(
            repository,
            sharedPrefs,
            interactor,
            screenState,
            detailsScreenState,
            resources
        )
    }

    override fun onCreate() {
        super.onCreate()

        url = getString(R.string.base_url)
    }

    companion object {
        private const val STR_APPS_SHARED_PREFS_FILE_NAME = "app_preferences"
    }
}