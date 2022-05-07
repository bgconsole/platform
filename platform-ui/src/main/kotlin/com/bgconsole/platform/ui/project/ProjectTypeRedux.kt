package com.bgconsole.platform.ui.project

import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store

const val PLATFORM_PROJECT_TYPE = "platform.project.type"

class ProjectTypeRedux(store: Store) {

    class RegisterProjectType(val projectType: ProjectType) : Action(PLATFORM_PROJECT_TYPE)
    class NewInstance(val project: Project) : Action(PLATFORM_PROJECT_TYPE)

    init {
        store.registerReducer(ProjectTypeReducer())
        store.registerService(ProjectTypeService())
        store.addToStore(PLATFORM_PROJECT_TYPE, ProjectTypeContent.default())
    }

    private class ProjectTypeReducer : Reducer<ProjectTypeContent> {
        override fun getKey(): String = PLATFORM_PROJECT_TYPE

        override fun reduce(store: Store, action: Action): ProjectTypeContent {
            val current = store.get(PLATFORM_PROJECT_TYPE) as ProjectTypeContent
            return when (action) {
                is RegisterProjectType -> current.copy(types = current.types + (action.projectType.getProjectType() to action.projectType))
                else -> current
            }
        }
    }

    private class ProjectTypeService : Service {
        override fun getKey(): String = PLATFORM_PROJECT_TYPE

        override fun execute(store: Store, action: Action) {
            when (action) {
                is NewInstance -> newInstance(store, action)
            }
        }

        private fun newInstance(store: Store, action: NewInstance) {
            val current = store.get(PLATFORM_PROJECT_TYPE) as ProjectTypeContent
            current.types[action.project.type]?.factory(store, action.project)?.let {
                store.dispatch(ProjectPerspectiveRedux.RegisterProjectPerspective(it))
                store.dispatch(ProjectPerspectiveRedux.SwitchProject(it.getProjectId()))
            }
        }

    }
}

data class ProjectTypeContent(val types: Map<String, ProjectType>) {
    companion object {
        fun default() = ProjectTypeContent(emptyMap())
    }
}