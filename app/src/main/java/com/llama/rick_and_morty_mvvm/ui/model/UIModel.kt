package com.llama.rick_and_morty_mvvm.ui.model

import androidx.lifecycle.MutableLiveData
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.domain.utils.SingleEventLiveData


class UIModel(
        val liveDataList: MutableLiveData<List<SimpleCharacter>> = MutableLiveData(),
        val errorLayoutVisibility: MutableLiveData<Boolean> = MutableLiveData(),
        val progressBarVisibility: MutableLiveData<Boolean> = MutableLiveData(),
        val snackbarAction: MutableLiveData<Boolean> = SingleEventLiveData(),
        val isRetryButtonClicked: MutableLiveData<Boolean> = MutableLiveData()
)