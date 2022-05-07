package com.bgconsole.platform.ui.perspective.workspace

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.Perspective
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent

class WorkspacePerspective : Perspective() {

    private lateinit var store: Store
    private lateinit var node: Node
    private lateinit var controller: WorkspacePerspectiveController

    override fun getId(): String = "com.bgconsole.platform.perspective.workspace"

    override fun getNode(): Node {
        return node
    }

    override fun setStore(store: Store) {
        this.store = store
    }

    override fun onInit() {
        val resource =
            javaClass.getResource("/com/bgconsole/platform/ui/perspective/workspace/workspace_perspective.fxml")
        val loader = FXMLLoader(resource)
        val root = loader.load<Parent>()
        controller = loader.getController()
        controller.setStore(store)
        root.stylesheets.add(javaClass.getResource("/com/bgconsole/platform/ui/styles.css")?.toExternalForm())
        node = root
    }

    override fun onClose() {
    }

    override fun onGetVisibility() {
    }

    override fun onLoseVisibility() {
    }

}