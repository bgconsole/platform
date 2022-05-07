package com.bgconsole.platform.ui

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.perspective.PerspectiveContent
import javafx.application.HostServices
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import java.util.*

class PlatformWindow(stage: Stage, private val store: Store, hostServices: HostServices) {

    private var controller: PlatformWindowController

    init {
        val resource = javaClass.getResource("/com/bgconsole/platform/ui/platform_window.fxml")
        val loader = FXMLLoader(resource)
        val root = loader.load<Parent>()
        controller = loader.getController()
        controller.setStore(store)
        controller.setHostServices(hostServices)
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


    }

}