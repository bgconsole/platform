package com.bgconsole.platform.ui.perspective.workspace

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.Perspective
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent

class WorkspacePerspective : Perspective() {

    private var node: Node? = null
    private var controller: WorkspacePerspectiveController? = null

    init {
        val resource =
            javaClass.getResource("/com/bgconsole/platform/ui/perspective/workspace/workspace_perspective.fxml")
        val loader = FXMLLoader(resource)
        val root = loader.load<Parent>()
        controller = loader.getController()
//        val scene = Scene(root)
        root.stylesheets.add(javaClass.getResource("/com/bgconsole/platform/ui/styles.css")?.toExternalForm())
        node = root
    }

    override fun getNode(): Node {
        return node!!
    }

    override fun setStore(store: Store) {

    }
}