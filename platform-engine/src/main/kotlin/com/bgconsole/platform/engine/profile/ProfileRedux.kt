package com.bgconsole.platform.engine.profile

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.store.Action

const val ENGINE_USER_SESSION_PROFILE = "engine.user.session.profile"

class ProfileRedux {

    class LoadProfiles : Action(ENGINE_USER_SESSION_PROFILE)
    class LoadProfilesSucceeded(val profiles: List<Profile>) : Action(ENGINE_USER_SESSION_PROFILE)

    class SaveProfile(val profile: Profile) : Action(ENGINE_USER_SESSION_PROFILE)
    class SaveProfileSucceeded() : Action(ENGINE_USER_SESSION_PROFILE)
}

typealias LoadProfile = ProfileRedux.LoadProfiles
typealias LoadProfileSucceeded = ProfileRedux.LoadProfilesSucceeded

typealias SaveProfile = ProfileRedux.SaveProfile
typealias SaveProfileSucceeded = ProfileRedux.SaveProfileSucceeded