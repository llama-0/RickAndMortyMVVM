package com.llama.rick_and_morty_mvvm.ui.viewmodel

import com.llama.rick_and_morty_mvvm.data.RepositoryImpl
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.base.BaseViewModel
import com.llama.rick_and_morty_mvvm.ui.base.Command
import com.llama.rick_and_morty_mvvm.ui.base.RefreshableScreenState
import com.llama.rick_and_morty_mvvm.ui.model.HomeScreenState

class HomeViewModel(private val repository: RepositoryImpl, model: RefreshableScreenState<*>) : BaseViewModel<
        RefreshableScreenState<*>,
        Command>(model) {

//    private val command: MutableLiveData<Command> = SingleEventLiveData() // ??

    private fun updateScreenState(
            screenState: HomeScreenState = this.model,
            dataListWithState: List<SimpleCharacter> = screenState.dataList
    ) {
    }

//    private val uiModel = UIModel()
//
//    val errorState: LiveData<Boolean> = uiModel.errorLayoutVisibility
//    val loadState: LiveData<Boolean> = uiModel.progressBarVisibility
//    val snackbarMessage: LiveData<Boolean> = uiModel.snackbarAction
//    val dataList: LiveData<List<SimpleCharacter>> = uiModel.liveDataList
//
//    init {
//        Log.d(TAG, "viewModel: init")
//        loadCharacters()
//    }
//
//    fun onButtonRetryClicked() {
//        loadCharacters()
//        uiModel.isRetryButtonClicked.value = true
//    }
//
//    private fun loadCharacters() {
//        uiModel.progressBarVisibility.value = true
//        repository.getCharacters(object : Resource {
//            override fun onError() {
//                setErrorState()
//            }
//            override fun onSuccess(data: List<SimpleCharacter>) {
//                uiModel.liveDataList.value = data
//                setSuccessState()
//            }
//        })
//    }
//
//    fun setErrorState() {
//        if (uiModel.isRetryButtonClicked.value == true) {
//            uiModel.snackbarAction.value = true
//        }
//        uiModel.errorLayoutVisibility.value = true
//        uiModel.progressBarVisibility.value = false
//    }
//
//    fun setSuccessState() {
//        uiModel.isRetryButtonClicked.value = false
//        uiModel.snackbarAction.value = false
//        uiModel.progressBarVisibility.value = false
//        uiModel.errorLayoutVisibility.value = false
//    }

    companion object {
        private const val TAG = "TAG"
    }
}