package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.*

class HomeViewModel(
    private val repository: RepositoryImpl,
    model: HomeScreenState<*>,
    private var snackbarCommand: ShowSnackbar
) :
    BaseViewModel<
            HomeScreenState<*>,
            Command>(model) {

    init {
        Log.d(TAG, "viewModel: init: loadCharacters()")
        loadCharacters()
    }

    // пока что получается, что shouldRefreshView == true всегда
    private fun updateScreenState(
        screenState: HomeScreenState<*> = this.model,
        dataListState: MutableLiveData<List<SimpleCharacter>> = screenState.dataList,
        errorLayoutVisibilityState: MutableLiveData<Boolean> = screenState.errorLayoutVisibility,
        progressBarVisibilityState: MutableLiveData<Boolean> = screenState.progressBarVisibility,
        isSnackbarActionRequiredState: MutableLiveData<Boolean> = screenState.isSnackbarActionRequired,
        shouldRefreshView: Boolean = true
    ) {
        this.model = HomeScreenState<List<SimpleCharacter>>(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState,
            isSnackbarActionRequiredState
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    fun onButtonRetryClicked() {
        loadCharacters()
        if (model.isSnackbarActionRequired.value == true) {
            Log.e("TAG", "onButtonRetryClicked: before executeCommand(snackbarCommand) called")
            executeCommand(snackbarCommand)
        }
    }

    // страшный копипаст код. Где бы один раз вызывать updateScreenState() ?
    private fun loadCharacters() {
        setLoadingState()

        repository.getCharacters(object : Resource {
            override fun onError() {
                setErrorState()
                updateScreenState(
                    model,
                    model.dataList,
                    model.errorLayoutVisibility,
                    model.progressBarVisibility,
                    model.isSnackbarActionRequired,
                    true
                )
            }

            override fun onSuccess(data: List<SimpleCharacter>) {
                model.dataList.value = data
                setSuccessState()
                updateScreenState(
                    model,
                    model.dataList,
                    model.errorLayoutVisibility,
                    model.progressBarVisibility,
                    model.isSnackbarActionRequired,
                    true
                )
            }
        })
    }

    private fun setErrorState() {
        Log.d(TAG, "setErrorState: inside")
        model.isSnackbarActionRequired.value = true
        model.errorLayoutVisibility.value = true
        model.progressBarVisibility.value = false
    }

    private fun setSuccessState() {
        Log.d(TAG, "setSuccessState: inside")
        model.isSnackbarActionRequired.value = false
        model.progressBarVisibility.value = false
        model.errorLayoutVisibility.value = false
    }

    private fun setLoadingState() {
        model.progressBarVisibility.value = true
    }

    companion object {
        private const val TAG = "TAG"
    }
}