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
    private val filteredList: List<SimpleCharacter>
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

    fun filter(type: GenderType, chipsState: List<Boolean>): List<SimpleCharacter> {
        val mutableList: MutableList<SimpleCharacter> = mutableListOf()
        when (type) {
            FEMALE -> getFemales()
            MALE -> getMales()
            GENDERLESS -> getGenderless()
            UNKNOWN -> getCharactersWithUnknownGender()
        }
        return mutableList
    }

//    /**
//     * Given list of [genders] filters [list] of SimpleCharacters by each present gender.
//     * Returns original list if no gender present or filtered list, sorted by id in ascending order otherwise.
//     * */
//    fun filterByGender(genders: List<String>): List<SimpleCharacter> {
//        val mutableList: MutableList<SimpleCharacter> = mutableListOf()
//        genders.forEach { gender ->
//            mutableList.addAll(
//                applyFilter(gender)
//            )
//        }
//        return if (genders.isEmpty()) list else mutableList.sortedBy { it.id }
//    }
//
//    private fun applyFilter(gender: String): List<SimpleCharacter> = when (gender) {
//        STR_FEMALE_DEFAULT, STR_FEMALE_RU -> {
//            getFemales()
//        }
//
//        STR_MALE_DEFAULT, STR_MALE_RU -> {
//            getMales()
//        }
//
//        STR_GENDERLESS_DEFAULT, STR_GENDERLESS_RU -> {
//            getGenderless()
//        }
//
//        STR_UNKNOWN_GENDER_DEFAULT, STR_UNKNOWN_GENDER_RU -> {
//            getCharactersWithUnknownGender()
//        }
//
//        else -> throw Exception("applyFilter(): `gender` doesn't match any gender type")
//    }
//
//    companion object {
//        private const val STR_FEMALE_DEFAULT = "Female"
//        private const val STR_FEMALE_RU = "Женский"
//        private const val STR_MALE_DEFAULT = "Male"
//        private const val STR_MALE_RU = "Мужской"
//        private const val STR_GENDERLESS_DEFAULT = "Genderless"
//        private const val STR_GENDERLESS_RU = "Бесполый"
//        private const val STR_UNKNOWN_GENDER_DEFAULT = "Unknown"
//        private const val STR_UNKNOWN_GENDER_RU = "Неизвестен"
//    }
}