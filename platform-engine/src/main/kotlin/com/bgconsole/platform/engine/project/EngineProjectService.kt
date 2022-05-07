package com.bgconsole.platform.engine.project

import com.bgconsole.platform.service.project.ProjectService
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store

class EngineProjectService(
    private val projectService: ProjectService
) : Service {
    override fun getKey(): String = ENGINE_USER_SESSION_PROJECT

    override fun execute(store: Store, action: Action) {
        when (action) {
            is LoadProjectsByWorkspace -> projectService.findByWorkspaceLocation(action.workspaceLocation)
                .let { store.dispatch(LoadProjectsSucceeded(it)) }
        }
    }

}