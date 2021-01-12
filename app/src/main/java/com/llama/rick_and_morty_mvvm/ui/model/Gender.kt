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
        var females: List<SimpleCharacter> = emptyList() // weak code or OK?
        var males: List<SimpleCharacter> = emptyList()
        var genderless: List<SimpleCharacter> = emptyList()
        var unknown: List<SimpleCharacter> = emptyList()
        genders.forEach { gender ->
            when (gender) {
                "Female", "Женский" -> females = getFemales()
                "Male", "Мужской" -> males = getMales()
                "Genderless", "Бесполый" -> genderless = getGenderless()
                "Unknown", "Неизвестен" -> unknown = getCharactersWithUnknownGender()
            }
        }
        val result: List<SimpleCharacter> =
            listOf(females, males, genderless, unknown).flatten().sortedBy { it.id }
        return if (genders.isEmpty()) list else result
    }
}