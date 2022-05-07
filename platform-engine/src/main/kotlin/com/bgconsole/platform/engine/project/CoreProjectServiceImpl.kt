package com.bgconsole.platform.engine.project

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.domain.LocationType
import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.engine.common.YAMLParser
import com.bgconsole.platform.service.project.ProjectService
import java.nio.file.Paths


class CoreProjectServiceImpl : ProjectService {

    private val yamlParser = YAMLParser(Project.empty(), emptyList())

    override fun findByWorkspaceLocation(location: Location): List<Project> {
        return Paths.get(location.location, "projects").toFile().listFiles { file -> file.isDirectory }
            ?.map { findByLocation(Location.technical(LocationType.FILE, it.absolutePath)) }.orEmpty()
    }

    override fun findByLocation(location: Location): Project {
        return yamlParser.readOne(Paths.get(location.location, "project.yaml").toString()).copy(location = location)
    }

    override fun findById(id: String): Project? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Project> {
        TODO("Not yet implemented")
    }

    override fun add(t: Project) {
        TODO("Not yet implemented")
    }

    override fun update(id: String, newT: Project): Project {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
}