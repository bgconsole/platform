package com.bgconsole.platform.ui

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.engine.PlatformCoreService
import com.bgconsole.platform.engine.profile.ProfileRedux.LoadProfiles
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.component.ComponentRedux
import com.bgconsole.platform.ui.perspective.PerspectiveRedux
import com.bgconsole.platform.ui.perspective.desk.DeskPerspective
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
        ComponentRedux(store)
    }

    private fun loadDefaultPerspective(store: Store) {
        val deskPerspective = DeskPerspective()
        deskPerspective.setStore(store)
        deskPerspective.onInit()
        store.dispatch(PerspectiveRedux.RegisterPerspective(deskPerspective))
        store.dispatch(PerspectiveRedux.SwitchPerspective(deskPerspective.getId()))
    }

    private fun loadDefaultData(store: Store) {
        store.dispatch(LoadProfiles())
    }
}