package com.igonris.rickandmortyapp.data.entity.filter

enum class Gender{
    none,
    female,
    male,
    genderless,
    unknown;

    fun parseName(): String {
        return if(this == Gender.none) ""
        else this.name
    }
}
