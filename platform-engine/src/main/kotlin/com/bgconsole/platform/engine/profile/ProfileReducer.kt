package com.bgconsole.platform.engine.profile

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.store.Action
import com.bgconsole.platform.store.Reducer
import com.bgconsole.platform.store.Store

class ProfileReducer : Reducer<List<Profile>> {
    override fun getKey(): String {
        return ENGINE_USER_SESSION_PROFILE
    }

    override fun reduce(store: Store, action: Action): List<Profile> {
        return when (action) {
            is LoadProfileSucceeded -> loadProfileSucceededReducer(store, action)
            else -> emptyList()
        }
    }

    private fun loadProfileSucceededReducer(store: Store, action: LoadProfileSucceeded): List<Profile> {
        return action.profiles
    }
}