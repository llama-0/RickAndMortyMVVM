package com.llama.rick_and_morty_mvvm.ui.model

import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.ActionLiveData

class UIModel(
        val liveDataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData(),
        val recyclerViewVisibility: MutableLiveData<Boolean> = MutableLiveData(),
        val errorLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(),
        val progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData(),
        val snackbarAction: ActionLiveData<Boolean> = ActionLiveData(), // crash
        val isRetryButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
)