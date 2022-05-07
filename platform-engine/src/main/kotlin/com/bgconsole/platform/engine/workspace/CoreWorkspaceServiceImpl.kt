package com.bgconsole.platform.engine.workspace

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.engine.common.YAMLParser
import com.bgconsole.platform.service.workspace.WorkspaceService
import java.nio.file.Paths

class CoreWorkspaceServiceImpl : WorkspaceService {

    private val yamlParser = YAMLParser(Workspace.empty(), emptyList())

    override fun findByLocation(location: Location): Workspace {
        return yamlParser.readOne(Paths.get(location.location, "workspace.yaml").toString()).copy(location = location)
    }

    override fun findById(id: String): Workspace? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Workspace> {
        TODO("Not yet implemented")
    }

    override fun add(t: Workspace) {
        TODO("Not yet implemented")
    }

    override fun update(id: String, newT: Workspace): Workspace {
        TODO("Not yet implemented")
    }

    override fun delete(id: String) {
        TODO("Not yet implemented")
    }
}