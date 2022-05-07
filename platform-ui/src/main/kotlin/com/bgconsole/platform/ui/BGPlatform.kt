package com.bgconsole.platform.ui

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.engine.PlatformCoreService
import com.bgconsole.platform.engine.profile.ProfileRedux.LoadProfiles
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.perspective.PerspectiveRedux
import com.bgconsole.platform.ui.perspective.workspace.WorkspacePerspective
import com.bgconsole.platform.ui.project.ProjectPerspectiveRedux
import com.bgconsole.platform.ui.project.ProjectTypeRedux
import javafx.application.HostServices
import javafx.stage.Stage

class BGPlatform(dir: Location, store: Store, stage: Stage, hostServices: HostServices) {

    init {
        PlatformCoreService(store)
        loadUIService(store)

        PlatformWindow(stage, store, hostServices)

        loadDefaultData(store)
        loadDefaultPerspective(store)
    }

    private fun loadUIService(store: Store) {
        PerspectiveRedux(store)
        ProjectPerspectiveRedux(store)
        ProjectTypeRedux(store)
    }

    private fun loadDefaultPerspective(store: Store) {
        val workspacePerspective = WorkspacePerspective()
        workspacePerspective.setStore(store)
        workspacePerspective.onInit()
        store.dispatch(PerspectiveRedux.RegisterPerspective(workspacePerspective))
        store.dispatch(PerspectiveRedux.SwitchPerspective(workspacePerspective.getId()))
    }

    private fun loadDefaultData(store: Store) {
        store.dispatch(LoadProfiles())
    }
}