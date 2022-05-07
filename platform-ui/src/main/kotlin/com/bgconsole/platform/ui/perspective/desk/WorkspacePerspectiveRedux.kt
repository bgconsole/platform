package com.bgconsole.platform.ui.perspective.desk

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.engine.project.ProjectRedux
import com.bgconsole.platform.engine.workspace.LoadWorkspacesByProfile
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store

const val UI_MAIN_WINDOW = "ui.main_window"

class WorkspacePerspectiveRedux(store: Store) {

    class SelectProfile(val profile: Profile) : Action(UI_MAIN_WINDOW)
    class SelectWorkspace(val workspace: Workspace) : Action(UI_MAIN_WINDOW)

    init {
        store.registerReducer(MainWindowReducer())
        store.registerService(MainWindowService())
        store.addToStore(UI_MAIN_WINDOW, WorkspacePerspectiveContent.default())
    }

    private class MainWindowReducer : Reducer<WorkspacePerspectiveContent> {
        override fun getKey(): String {
            return UI_MAIN_WINDOW
        }

        override fun reduce(store: Store, action: Action): WorkspacePerspectiveContent {
            val current = store.get(UI_MAIN_WINDOW) as WorkspacePerspectiveContent
            return when (action) {
                is SelectProfile -> selectProfile(store, action, current)
                is SelectWorkspace -> selectWorkspace(store, action, current)
                else -> current
            }
        }

        private fun selectProfile(store: Store, action: SelectProfile, current: WorkspacePerspectiveContent): WorkspacePerspectiveContent {
            return current.copy(selectedProfile = action.profile)
        }

        private fun selectWorkspace(
            store: Store,
            action: SelectWorkspace,
            current: WorkspacePerspectiveContent
        ): WorkspacePerspectiveContent {
            return current.copy(selectedWorkspace = action.workspace)
        }

    }

    private class MainWindowService : Service {
        override fun getKey(): String {
            return UI_MAIN_WINDOW
        }

        override fun execute(store: Store, action: Action) {
            when (action) {
                is SelectProfile -> selectProfile(store, action)
                is SelectWorkspace -> selectWorkspace(store, action)
            }
        }

        private fun selectWorkspace(store: Store, action: SelectWorkspace) {
            action.workspace.location?.let { store.dispatch(ProjectRedux.LoadProjectsByWorkspace(it)) }
        }

        private fun selectProfile(store: Store, action: SelectProfile) {
            store.dispatch(LoadWorkspacesByProfile(action.profile.id))
        }

    }
}