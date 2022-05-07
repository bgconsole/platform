package com.bgconsole.platform.ui

import com.bgconsole.platform.domain.Location
import com.bgconsole.platform.engine.PlatformCoreService
import com.bgconsole.platform.engine.profile.ProfileRedux.LoadProfiles
import com.bgconsole.platform.store.Store
import javafx.stage.Stage

class BGPlatform(dir: Location, store: Store, stage: Stage) {

    init {
        PlatformCoreService(store)
        PlatformWindow(stage, store)

        loadDefault(store)
    }

    private fun loadDefault(store: Store) {
        store.dispatch(LoadProfiles())
    }
}