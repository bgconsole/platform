package com.bgconsole.platform.service.project

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.service.common.CRUDService

interface ProjectService : CRUDService<Project> {

    fun findByWorkspaceLocation(location: Location): List<Project>

    fun findByLocation(location: Location): Project
}