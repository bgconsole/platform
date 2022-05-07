package com.bgconsole.platform.engine.project

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.store.Action

const val ENGINE_USER_SESSION_PROJECT = "engine.user.session.project"

class ProjectRedux {

    class LoadProjectsByWorkspace(val workspaceLocation: Location) : Action(ENGINE_USER_SESSION_PROJECT)
    class LoadProjectsSucceeded(val projects: List<Project>) : Action(ENGINE_USER_SESSION_PROJECT)
}

typealias LoadProjectsByWorkspace = ProjectRedux.LoadProjectsByWorkspace
typealias LoadProjectsSucceeded = ProjectRedux.LoadProjectsSucceeded