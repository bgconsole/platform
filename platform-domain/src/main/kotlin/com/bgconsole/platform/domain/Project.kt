package com.bgconsole.platform.domain

data class Project(
    val id: String,
    val name: String,
    val description: String?,
    val location: Location?,
) {
    companion object {
        fun empty(): Project {
            return Project("", "", null, null)
        }
    }
}
