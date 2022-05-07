package com.bgconsole.platform.ui.perspective.workspace

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.domain.Workspace

data class WorkspacePerspectiveContent(val selectedProfile: Profile?, val selectedWorkspace: Workspace?) {
    companion object {
        fun default(): WorkspacePerspectiveContent {
            return WorkspacePerspectiveContent(null, null)
        }
    }
}
