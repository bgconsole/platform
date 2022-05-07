package com.bgconsole.platform.engine

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.engine.profile.CoreProfileServiceImpl
import com.bgconsole.platform.engine.profile.ENGINE_USER_SESSION_PROFILE
import com.bgconsole.platform.engine.profile.EngineProfileService
import com.bgconsole.platform.engine.profile.ProfileReducer
import com.bgconsole.platform.engine.project.CoreProjectServiceImpl
import com.bgconsole.platform.engine.project.ENGINE_USER_SESSION_PROJECT
import com.bgconsole.platform.engine.project.EngineProjectService
import com.bgconsole.platform.engine.project.ProjectReducer
import com.bgconsole.platform.engine.workspace.CoreWorkspaceServiceImpl
import com.bgconsole.platform.engine.workspace.ENGINE_USER_SESSION_WORKSPACE
import com.bgconsole.platform.engine.workspace.EngineWorkspaceService
import com.bgconsole.platform.engine.workspace.WorkspaceReducer
import com.bgconsole.platform.store.Store

class PlatformCoreService(store: Store) {

    private val coreProfileService = CoreProfileServiceImpl()
    private val profileService = EngineProfileService(coreProfileService)

    private val coreWorkspaceService = CoreWorkspaceServiceImpl()
    private val workspaceService = EngineWorkspaceService(coreProfileService, coreWorkspaceService)

    private val coreProjectService = CoreProjectServiceImpl()
    private val projectService = EngineProjectService(coreProjectService)

    init {
        store.registerServices(
            listOf(
                profileService,
                workspaceService,
                projectService,
            )
        )
        store.registerReducers(
            listOf(
                ProfileReducer(),
                WorkspaceReducer(),
                ProjectReducer(),
            )
        )
        store.addToStore(ENGINE_USER_SESSION_PROFILE, emptyList<Profile>())
        store.addToStore(ENGINE_USER_SESSION_WORKSPACE, emptyList<Workspace>())
        store.addToStore(ENGINE_USER_SESSION_PROJECT, emptyList<Project>())
    }
}