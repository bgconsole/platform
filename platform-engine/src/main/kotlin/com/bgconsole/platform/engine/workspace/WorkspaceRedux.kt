package com.bgconsole.platform.engine.workspace

import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.store.Action

const val ENGINE_USER_SESSION_WORKSPACE = "engine.user.session.workspace"

class WorkspaceRedux {

    class LoadWorkspacesByProfile(val profileId: String) : Action(ENGINE_USER_SESSION_WORKSPACE)
    class LoadWorkspacesSucceeded(val workspaces: List<Workspace>) : Action(ENGINE_USER_SESSION_WORKSPACE)
}

typealias LoadWorkspacesByProfile = WorkspaceRedux.LoadWorkspacesByProfile
typealias LoadWorkspacesSucceeded = WorkspaceRedux.LoadWorkspacesSucceeded