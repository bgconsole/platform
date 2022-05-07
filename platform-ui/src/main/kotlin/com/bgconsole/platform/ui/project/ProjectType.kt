package com.bgconsole.platform.ui.project

import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.store.Store

abstract class ProjectType {

    abstract fun getProjectType(): String

    abstract fun getProjectName(): String

    abstract fun factory(store: Store, project: Project): ProjectPerspective
}