package com.bgconsole.platform.engine.workspace

import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Store

class WorkspaceReducer : Reducer<List<Workspace>> {
    override fun getKey(): String {
        return ENGINE_USER_SESSION_WORKSPACE
    }

    override fun reduce(store: Store, action: Action): List<Workspace> {
        return when (action) {
            is LoadWorkspacesSucceeded -> loadWorkspaceSucceeded(store, action)
            else -> store.get(ENGINE_USER_SESSION_WORKSPACE) as List<Workspace>
        }
    }

    private fun loadWorkspaceSucceeded(store: Store, action: LoadWorkspacesSucceeded): List<Workspace> {
        return action.workspaces
    }
}