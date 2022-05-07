package com.bgconsole.platform.service.workspace

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.service.common.CRUDService

interface WorkspaceService : CRUDService<Workspace> {

    fun findByLocation(location: Location): Workspace
}