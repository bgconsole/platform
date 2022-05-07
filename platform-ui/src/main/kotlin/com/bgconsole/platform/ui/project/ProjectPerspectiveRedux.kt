package com.bgconsole.platform.ui.project

import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.perspective.PerspectiveRedux

const val PLATFORM_PERSPECTIVE_PROJECT = "platform.iu.perspective.project"

class ProjectPerspectiveRedux(store: Store) {

    class RegisterProjectPerspective(val project: ProjectPerspective) : Action(PLATFORM_PERSPECTIVE_PROJECT)
    class SwitchProject(val projectId: String) : Action(PLATFORM_PERSPECTIVE_PROJECT)
    class CloseProject(val projectId: String) : Action(PLATFORM_PERSPECTIVE_PROJECT)

    init {
        store.registerReducer(ProjectPerspectiveReducer())
        store.registerService(ProjectPerspectiveService())
        store.addToStore(PLATFORM_PERSPECTIVE_PROJECT, ProjectPerspectiveContent.default())
    }

    private class ProjectPerspectiveReducer : Reducer<ProjectPerspectiveContent> {
        override fun getKey(): String = PLATFORM_PERSPECTIVE_PROJECT

        override fun reduce(store: Store, action: Action): ProjectPerspectiveContent {
            val current = store.get(PLATFORM_PERSPECTIVE_PROJECT) as ProjectPerspectiveContent
            return when (action) {
                is RegisterProjectPerspective -> current.copy(projects = current.projects + (action.project.getProjectId() to action.project))
                is CloseProject -> current.copy(projects = current.projects.filterKeys { it != action.projectId })
                else -> current
            }
        }
    }

    private class ProjectPerspectiveService : Service {
        override fun getKey(): String = PLATFORM_PERSPECTIVE_PROJECT

        override fun execute(store: Store, action: Action) {
            when (action) {
                is RegisterProjectPerspective -> store.dispatch(PerspectiveRedux.RegisterPerspective(action.project))
                is SwitchProject -> switchProject(store, action)
                is CloseProject -> closeProject(store, action)
            }
        }

        private fun closeProject(store: Store, action: CloseProject) {
            val current = store.get(PLATFORM_PERSPECTIVE_PROJECT) as ProjectPerspectiveContent
            current.projects[action.projectId]?.getId()?.let { store.dispatch(PerspectiveRedux.ClosePerspective(it)) }
        }

        private fun switchProject(store: Store, action: SwitchProject) {
            val current = store.get(PLATFORM_PERSPECTIVE_PROJECT) as ProjectPerspectiveContent
            current.projects[action.projectId]?.getId()?.let { store.dispatch(PerspectiveRedux.SwitchPerspective(it)) }
        }

    }
}

data class ProjectPerspectiveContent(val projects: Map<String, ProjectPerspective>) {
    companion object {
        fun default() = ProjectPerspectiveContent(emptyMap())
    }
}