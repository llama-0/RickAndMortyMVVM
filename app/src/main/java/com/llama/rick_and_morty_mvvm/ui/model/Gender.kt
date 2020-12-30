package com.llama.rick_and_morty_mvvm.ui.model

import android.content.res.Resources
import com.llama.rick_and_morty_mvvm.R
import com.llama.rick_and_morty_mvvm.domain.model.SimpleCharacter

class Gender(
    resources: Resources,
    private val list: List<SimpleCharacter>
) {

    private val femaleApiField: String = resources.getString(R.string.female_gender_api_field)
    private val maleApiField: String = resources.getString(R.string.male_gender_api_field)
    private val genderlessApiField: String =
        resources.getString(R.string.genderless_gender_api_field)
    private val unknownApiField: String = resources.getString(R.string.unknown_gender_api_field)

    private fun getFemales(): List<SimpleCharacter> =
        list.filter { it.gender == femaleApiField }

    private fun getMales(): List<SimpleCharacter> =
        list.filter { it.gender == maleApiField }

    private fun getGenderless(): List<SimpleCharacter> =
        list.filter { it.gender == genderlessApiField }

    private fun getCharactersWithUnknownGender(): List<SimpleCharacter> =
        list.filter { it.gender.equals(unknownApiField, true) }

    fun filterListByGender(genders: List<String>): List<SimpleCharacter> {
        var females: List<SimpleCharacter> = emptyList()
        var males: List<SimpleCharacter> = emptyList()
        var genderless: List<SimpleCharacter> = emptyList()
        var unknown: List<SimpleCharacter> = emptyList()
        genders.forEach { gender ->
            when (gender) {
                femaleApiField -> females = getFemales()
                maleApiField -> males = getMales()
                genderlessApiField -> genderless = getGenderless()
                unknownApiField -> unknown = getCharactersWithUnknownGender()
            }
        }
        val result: List<SimpleCharacter> =
            listOf(females, males, genderless, unknown).flatten().sortedBy { it.id }
        return if (genders.isEmpty()) list else result
    }
}