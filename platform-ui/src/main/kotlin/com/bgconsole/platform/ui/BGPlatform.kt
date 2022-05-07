package com.bgconsole.platform.ui

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.engine.PlatformCoreService
import com.bgconsole.platform.engine.profile.ProfileRedux.LoadProfiles
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.perspective.PerspectiveRedux
import com.bgconsole.platform.ui.perspective.workspace.WorkspacePerspective
import javafx.stage.Stage

class BGPlatform(dir: Location, store: Store, stage: Stage) {

    init {
        PlatformCoreService(store)
        loadUIService(store)

        PlatformWindow(stage, store)

        loadDefaultData(store)
        loadDefaultPerspective(store)
    }

    private fun loadUIService(store: Store) {
        PerspectiveRedux(store)
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