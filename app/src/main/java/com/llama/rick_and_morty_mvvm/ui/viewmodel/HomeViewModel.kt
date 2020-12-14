package com.llama.rick_and_morty_mvvm.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.data.network.Resource
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.base.Command
import com.llama.rick_and_morty_mvvm.ui.base.HomeScreenState
import com.llama.rick_and_morty_mvvm.ui.model.UIModel

class HomeViewModel(private val repository: RepositoryImpl, model: HomeScreenState<*>) :
    BaseViewModel<
            HomeScreenState<*>,
            Command>(model) {

    private var flag: Int = 0 // -1 -- refresh Error Screen, 1 -- refresh Success screen

    private val uiModel = UIModel()

    val errorState: LiveData<Boolean> = uiModel.errorLayoutVisibility
    val loadState: LiveData<Boolean> = uiModel.progressBarVisibility
    val snackbarMessage: LiveData<Boolean> = uiModel.snackbarAction
    val dataList: LiveData<List<SimpleCharacter>> = uiModel.liveDataList

    init {
        Log.d(TAG, "viewModel: init")
        loadCharacters()
    }

    private val command: MutableLiveData<Command> = SingleEventLiveData() // ?

    // кто должен вызывать эту функцию?
    private fun updateScreenState(
        screenState: HomeScreenState<*> = this.model,
        dataListWithState: List<SimpleCharacter> = screenState.dataList,
        uiModel: UIModel = screenState.uiModel,
        shouldRefreshView: Boolean = true
    ) {
        this.model = HomeScreenState<List<SimpleCharacter>>(dataListWithState, uiModel)
        if (shouldRefreshView) {
            refreshView()
        }
    }

    override fun refreshView() {
        super.refreshView()
        when (flag) {
            -1 -> setErrorState()
            1 -> setSuccessState()
            else -> setLoadingState()
        }
    }
//
//    fun onButtonRetryClicked() {
//        loadCharacters()
//        uiModel.isRetryButtonClicked.value = true
//    }
//
    private fun loadCharacters() {
//        uiModel.progressBarVisibility.value = true
        repository.getCharacters(object : Resource {
            override fun onError() {
//                setErrorState()
                flag = -1
            }

            override fun onSuccess(data: List<SimpleCharacter>) {
                uiModel.liveDataList.value = data
//                setSuccessState()
                flag = 1
            }
        })
    }

    private fun setErrorState() {
        if (uiModel.isRetryButtonClicked.value == true) {
            uiModel.snackbarAction.value = true
        }
        uiModel.errorLayoutVisibility.value = true
        uiModel.progressBarVisibility.value = false
    }

    private fun setSuccessState() {
        uiModel.isRetryButtonClicked.value = false
        uiModel.snackbarAction.value = false
        uiModel.progressBarVisibility.value = false
        uiModel.errorLayoutVisibility.value = false
    }

    private fun setLoadingState() {
        uiModel.progressBarVisibility.value = true
    }

    companion object {
        private const val TAG = "TAG"
    }
}