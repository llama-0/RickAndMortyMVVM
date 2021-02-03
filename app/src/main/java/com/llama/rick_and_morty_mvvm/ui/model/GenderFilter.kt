package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter
import com.llama.rick_and_morty_mvvm.ui.model.GenderType.*

/**
 * [GenderFilter] class holds filtering logic of
 * [list] of SimpleCharacters by mutually exclusive gender type
 * */
class GenderFilter(
    resources: Resources,
    private val list: List<SimpleCharacter>,
    private val genderTypes: Set<GenderType>
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

    fun filter(): List<SimpleCharacter> {
        val filteredList: MutableList<SimpleCharacter> = mutableListOf()
        genderTypes.forEach {
            filteredList.addAll(
                when (it) {
                    FEMALE -> getFemales()
                    MALE -> getMales()
                    GENDERLESS -> getGenderless()
                    UNKNOWN -> getCharactersWithUnknownGender()
                }
            )
        }
        return if (genderTypes.isEmpty()) list else filteredList.sortedBy { it.id }
    }
}