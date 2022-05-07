package com.bgconsole.platform.ui.project

import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.ui.Perspective

abstract class ProjectPerspective : Perspective() {

    abstract fun getProjectId(): String

    abstract fun setProject(project: Project)

    abstract fun getProject(): Project

    abstract fun getProjectType(): String
}