package com.bgconsole.platform.ui

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.store.Subscriber
import com.bgconsole.platform.ui.perspective.PLATFORM_PERSPECTIVE
import com.bgconsole.platform.ui.perspective.PerspectiveContent
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.layout.StackPane

class PlatformWindowController {

    @FXML
    lateinit var appContent: StackPane

    private lateinit var perspectiveContent: PerspectiveContent

    private lateinit var store: Store

    fun setStore(store: Store) {
        this.store = store

        val newPerspective = store.get(PLATFORM_PERSPECTIVE) as PerspectiveContent
        updatePerspective(newPerspective)
        store.subscribe(PLATFORM_PERSPECTIVE, object : Subscriber {
            override fun update(entity: Any) {
                updatePerspective(entity as PerspectiveContent)
            }
        })
    }

    private fun updatePerspective(newPerspective: PerspectiveContent) {
        if (newPerspective.perspectiveStack.isNotEmpty()) {
            if (perspectiveContent.perspectiveStack.isNotEmpty()) {
                val currentId = perspectiveContent.perspectiveStack.first().getId()
                if (currentId != newPerspective.perspectiveStack.first().getId()) {
                    appContent.children.removeFirst()
                    appContent.children.add(newPerspective.perspectiveStack.first().getNode())
                }
            } else {
                appContent.children.add(newPerspective.perspectiveStack.first().getNode())
            }
        }
        perspectiveContent = newPerspective
    }

    fun openProfileManager(actionEvent: ActionEvent) {

    }

    fun quit(actionEvent: ActionEvent) {

    }

    fun newWorkspace(actionEvent: ActionEvent) {

    }

    fun openWorkspace(actionEvent: ActionEvent) {

    }

    fun removeWorkspace(actionEvent: ActionEvent) {

    }

    fun createProject(actionEvent: ActionEvent) {

    }

    fun help(actionEvent: ActionEvent) {

    }

    fun about(actionEvent: ActionEvent) {

    }


}