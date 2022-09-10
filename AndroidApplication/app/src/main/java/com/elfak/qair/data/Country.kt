package com.elfak.qair.data

data class Country(val name: String = "") {
    override fun toString(): String {
        return name
    }
}
