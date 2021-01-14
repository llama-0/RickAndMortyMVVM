package com.llama.rick_and_morty_mvvm

import android.app.Application
import android.content.SharedPreferences
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.domain.interactor.CharactersInteractor
import com.llama.rick_and_morty_mvvm.data.mapper.CharactersMapper
import com.llama.rick_and_morty_mvvm.data.network.ApiService
import com.llama.rick_and_morty_mvvm.data.network.ApiServiceBuilder
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharacterDetailsScreenState
import com.llama.rick_and_morty_mvvm.ui.view.screenstate.CharactersScreenState
import com.llama.rick_and_morty_mvvm.ui.viewmodel.CharactersViewModelFactory

class App : Application() {

    private val sharedPrefs: SharedPreferences by lazy {
        getSharedPreferences(STR_PREF_FILE_NAME, MODE_PRIVATE)
    }

    private val apiService: ApiService by lazy {
        ApiServiceBuilder(STR_BASE_URL).buildService()
    }

    private val charactersMapper: CharactersMapper by lazy {
        CharactersMapper()
    }

    private val repository: RepositoryImpl by lazy {
        RepositoryImpl(apiService, charactersMapper)
    }

    private val interactor: CharactersInteractor by lazy {
        CharactersInteractor(repository)
    }

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
            INT_ID,
            STR_NAME,
            STR_GENDER,
            STR_STATUS,
            STR_SPECIES,
            STR_IMG_URL,
            STR_FROM,
            STR_TO
        )
    }

    private val detailsScreenState: CharacterDetailsScreenState by lazy {
        CharacterDetailsScreenState(defaultCharacter)
    }

    val factory: CharactersViewModelFactory by lazy {
        CharactersViewModelFactory(
            sharedPrefs,
            interactor,
            screenState,
            detailsScreenState,
            resources
        )
    }

    companion object {
        private const val STR_PREF_FILE_NAME = "app_preferences"
        private const val STR_BASE_URL = "https://rickandmortyapi.com/api/"
        private const val INT_ID = -1
        private const val STR_NAME = "Ally"
        private const val STR_GENDER = "Female"
        private const val STR_STATUS = "Alive"
        private const val STR_SPECIES = "Human"
        private const val STR_IMG_URL = "https://google.com"
        private const val STR_FROM = "Earth"
        private const val STR_TO = "Mars"
    }
}