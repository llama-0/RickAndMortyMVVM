package com.llama.rick_and_morty_mvvm.presentation.mappers

import com.llama.rick_and_morty_mvvm.presentation.models.GenderType

class ChipIdToGenderType {

    fun map(chipIds: List<Int>): List<Pair<Int, GenderType>> =
        chipIds.zip(GenderType.values())
}