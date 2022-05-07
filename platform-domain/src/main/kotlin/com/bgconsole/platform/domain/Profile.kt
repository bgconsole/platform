package com.bgconsole.platform.domain

import java.util.*

data class Profile(
    val id: String,
    val name: String,
    val description: String?,
    val locations: List<Location>? = emptyList()
) {
    companion object Factory {
        fun empty(): Profile {
            return Profile("", "", null)
        }

        fun profile(name: String, description: String?): Profile {
            return Profile(UUID.randomUUID().toString(), name, description)
        }
    }
}