package com.bgconsole.platform.engine.workspace

import com.bgconsole.platform.service.profile.ProfileService
import com.bgconsole.platform.service.workspace.WorkspaceService
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store

class EngineWorkspaceService(
    private val profileService: ProfileService,
    private val workspaceService: WorkspaceService
) : Service {
    override fun getKey(): String {
        return ENGINE_USER_SESSION_WORKSPACE
    }

    override fun execute(store: Store, action: Action) {
        when (action) {
            is LoadWorkspacesByProfile -> loadWorkspacesByProfile(store, action)
        }
    }

    private fun loadWorkspacesByProfile(store: Store, action: LoadWorkspacesByProfile) {
        profileService.findById(action.profileId)?.let {
            it.locations?.map { location -> workspaceService.findByLocation(location) }?.let { workspaces ->
                store.dispatch(LoadWorkspacesSucceeded(workspaces))
            }
        }
    }
}