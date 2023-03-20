package com.igonris.rickandmortyapp.data.entity.filter

enum class Status {
    none,
    alive,
    dead,
    unknown;

    fun parseName(): String {
        return if(this == none) ""
        else this.name
    }
}