package com.bgconsole.platform.domain

data class Location(
    val id: String,
    val type: LocationType,
    val name: String,
    val location: String,
    val relativeTo: String? = ""
) {
    companion object {
        fun technical(type: LocationType, location: String): Location {
            return Location("", type, "", location)
        }
    }
}

enum class LocationType {
    FILE, DATABASE, REMOTE
}
