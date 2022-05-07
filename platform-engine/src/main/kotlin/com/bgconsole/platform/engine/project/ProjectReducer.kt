package com.bgconsole.platform.engine.project

import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Store

class ProjectReducer : Reducer<List<Project>> {
    override fun getKey(): String {
        return ENGINE_USER_SESSION_PROJECT
    }

    override fun reduce(store: Store, action: Action): List<Project> {
        return when (action) {
            is LoadProjectsSucceeded -> action.projects
            else -> store.get(ENGINE_USER_SESSION_PROJECT) as List<Project>
        }
    }

}