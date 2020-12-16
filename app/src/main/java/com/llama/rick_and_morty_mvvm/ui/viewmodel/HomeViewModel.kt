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
        loadCharacters()
    }

    // пока что получается, что shouldRefreshView == true всегда
    private fun updateScreenState(
        screenState: HomeScreenState<*> = this.model,
        dataListState: MutableLiveData<List<SimpleCharacter>> = screenState.dataList,
        errorLayoutVisibilityState: MutableLiveData<Boolean> = screenState.errorLayoutVisibility,
        progressBarVisibilityState: MutableLiveData<Boolean> = screenState.progressBarVisibility,
        shouldRefreshView: Boolean = true
    ) {
        this.model = HomeScreenState<List<SimpleCharacter>>(
            dataListState,
            errorLayoutVisibilityState,
            progressBarVisibilityState
        )
        if (shouldRefreshView) {
            Log.d(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    fun onButtonRetryClicked() {
        loadCharacters()
    }

    // страшный копипаст код. Где бы один раз вызвать updateScreenState() ?
    private fun loadCharacters() {
        setLoadingState()

        repository.getCharacters(object : Resource {
            override fun onError() {
                setErrorState()
                executeCommand(snackbarCommand)
                updateScreenState(
                    model,
                    model.dataList,
                    model.errorLayoutVisibility,
                    model.progressBarVisibility,
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
                    true
                )
            }
        })
    }

    private fun setErrorState() {
        model.errorLayoutVisibility.value = true
        model.progressBarVisibility.value = false
    }

    private fun setSuccessState() {
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