package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.base.Command
import com.llama.rick_and_morty_mvvm.ui.base.HomeScreenState

class HomeViewModel(private val repository: RepositoryImpl, model: HomeScreenState<*>) :
    BaseViewModel<
            HomeScreenState<*>,
            Command>(model) {

    init {
        Log.d(TAG, "viewModel: init: loadCharacters()")
        loadCharacters()
    }

    private val command: MutableLiveData<Command> = SingleEventLiveData() // ?

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
            Log.e(TAG, "updateScreenState: refreshing view")
            refreshView()
        }
    }

    //
//    fun onButtonRetryClicked() {
//        loadCharacters()
//        uiModel.isRetryButtonClicked.value = true
//    }
//

    // страшный копипаст код. Логичнее казалось вызывать updateScreenState() после loadCharacters() один раз
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
//        Log.e(TAG, "setErrorState: inside")
//        if (uiModel.isRetryButtonClicked.value == true) {
//            uiModel.snackbarAction.value = true
//        }
        model.errorLayoutVisibility.value = true
        model.progressBarVisibility.value = false
    }

    private fun setSuccessState() {
//        Log.e(TAG, "setSuccessState: inside")
//        uiModel.isRetryButtonClicked.value = false
//        uiModel.snackbarAction.value = false
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