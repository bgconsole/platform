package com.bgconsole.platform.engine.profile

import com.bgconsole.platform.service.profile.ProfileService
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Service
import com.bgconsole.platform.store.Store

class EngineProfileService(private val profileService: ProfileService) : Service {

    override fun getKey(): String {
        return ENGINE_USER_SESSION_PROFILE
    }

    override fun execute(store: Store, action: Action) {
        when (action) {
            is LoadProfile -> loadProfiles(store, action)
            is SaveProfile -> saveProfile(store, action)
        }
    }

    private fun saveProfile(store: Store, action: SaveProfile) {
        profileService.add(action.profile)
        store.dispatch(SaveProfileSucceeded())
        store.dispatch(LoadProfile())
    }

    private fun loadProfiles(store: Store, action: LoadProfile) {
        val profiles = profileService.findAll()
        store.dispatch(LoadProfileSucceeded(profiles))
    }
}