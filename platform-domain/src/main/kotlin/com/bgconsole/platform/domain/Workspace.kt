package com.bgconsole.platform.domain

data class Workspace(
    val id: String,
    val name: String,
    val description: String?,
    val location: Location?,
    val projects: List<Project>?
) {
    companion object {
        fun empty(): Workspace {
            return Workspace("", "", "", null, emptyList())
        }
    }
}
