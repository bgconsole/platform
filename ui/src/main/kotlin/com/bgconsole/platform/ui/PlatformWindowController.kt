package com.bgconsole.platform.ui

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.Node
import javafx.scene.layout.StackPane

class PlatformWindowController {

    @FXML
    lateinit var appContent: StackPane

    fun setPerspective(node: Node) {
        appContent.children.add(0, node)
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