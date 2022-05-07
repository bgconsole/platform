package com.bgconsole.platform.ui

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.perspective.workspace.WorkspacePerspective
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.util.*

class PlatformWindow(private val stage: Stage, private val store: Store) {

    private var controller: PlatformWindowController? = null

    init {
        val resource = javaClass.getResource("/com/bgconsole/platform/ui/platform_window.fxml")
        val loader = FXMLLoader(resource)
        val root = loader.load<Parent>()
        controller = loader.getController()
        val scene = Scene(root)
        stage.scene = scene
        scene.stylesheets.add(javaClass.getResource("/com/bgconsole/platform/ui/styles.css")?.toExternalForm())
        stage.title = "BG Console | Platform"
        stage.icons.add(
            Image(
                Objects.requireNonNull(
                    PlatformWindow::class.java.getResourceAsStream("/com/bgconsole/platform/ui/img/logo.png")
                )
            )
        )
        stage.scene = scene
        stage.show()
        loadDefaultPerspective()
    }

    private fun loadDefaultPerspective() {
        val workspacePerspective = WorkspacePerspective()
        workspacePerspective.setStore(store)
        val node = workspacePerspective.getNode()
        controller?.setPerspective(node)
    }
}