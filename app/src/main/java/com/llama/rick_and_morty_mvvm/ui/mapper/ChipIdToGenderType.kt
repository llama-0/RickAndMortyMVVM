package com.llama.rick_and_morty_mvvm.ui.mapper

import com.llama.rick_and_morty_mvvm.ui.model.GenderType

class ChipIdToGenderType {

    fun map(chipIds: List<Int>): List<Pair<Int, GenderType>> =
        chipIds.zip(GenderType.values())
}