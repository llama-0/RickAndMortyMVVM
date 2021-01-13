package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

/**
 * [Gender] class holds filtering logic of
 * [list] of SimpleCharacters by mutually exclusive gender type
 * */
class Gender(
    resources: Resources,
    private val genderTypes: GenderTypes,
    private val list: List<SimpleCharacter>
) {

    private val femaleApiField: String = resources.getString(R.string.female_gender_api_field)
    private val maleApiField: String = resources.getString(R.string.male_gender_api_field)
    private val genderlessApiField: String =
        resources.getString(R.string.genderless_gender_api_field)
    private val unknownApiField: String = resources.getString(R.string.unknown_gender_api_field)

    private fun getFemales(): List<SimpleCharacter> =
        list.filter { it.gender.equals(femaleApiField, true) }

    private fun getMales(): List<SimpleCharacter> =
        list.filter { it.gender.equals(maleApiField, true) }

    private fun getGenderless(): List<SimpleCharacter> =
        list.filter { it.gender.equals(genderlessApiField, true) }

    private fun getCharactersWithUnknownGender(): List<SimpleCharacter> =
        list.filter { it.gender.equals(unknownApiField, true) }

    /**
     * Given list of [genders] filters [list] of SimpleCharacters by each present gender.
     * Returns original list if no gender present or filtered list, sorted by id in ascending order otherwise.
     * */
    fun filterListByGender(genders: List<String>): List<SimpleCharacter> {
        val mutableList: MutableList<SimpleCharacter> = mutableListOf()
        genders.forEach { gender ->
            mutableList.addAll(
                applyFilter(gender)
            )
        }
        return if (genders.isEmpty()) list else mutableList.sortedBy { it.id }
    }

    private fun applyFilter(gender: String): List<SimpleCharacter> = when (gender) {
        genderTypes.types[0][0], genderTypes.types[0][1] -> {
            getFemales()
        }

        genderTypes.types[1][0], genderTypes.types[1][1] -> {
            getMales()
        }

        genderTypes.types[2][0], genderTypes.types[2][1] -> {
            getGenderless()
        }

        genderTypes.types[3][0], genderTypes.types[3][1] -> {
            getCharactersWithUnknownGender()
        }

        else -> emptyList()
    }
}