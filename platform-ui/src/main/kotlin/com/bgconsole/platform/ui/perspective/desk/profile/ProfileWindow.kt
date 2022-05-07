package com.bgconsole.platform.ui.perspective.desk.profile

import com.bgconsole.platform.engine.profile.ProfileRedux.LoadProfiles
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.PlatformWindow
import com.bgconsole.platform.ui.SimpleTrigger
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.StageStyle
import java.io.IOException
import java.util.*

class ProfileWindow(private val store: Store, trigger: SimpleTrigger) {
    private lateinit var stage: Stage
    private lateinit var controller: ProfileWindowController
    fun popUp() {
        refreshProfiles()
        if (stage.isIconified) stage.isIconified = false
        stage.show()
        stage.requestFocus()
        stage.toFront()
    }

    private fun refreshProfiles() {
        store.dispatch(LoadProfiles())
    }

    init {
        try {
            val resource = javaClass.getResource("/com/bgconsole/platform/ui/perspective/desk/profile_window.fxml")
            val loader = FXMLLoader(resource)
            val root = loader.load<Parent>()
            controller = loader.getController()
            controller.setStore(store)
            stage = Stage(StageStyle.UTILITY)
            stage.initModality(Modality.WINDOW_MODAL)
            val scene = Scene(root)
            stage.scene = scene
            scene.stylesheets.add(javaClass.getResource("/com/bgconsole/platform/ui/styles.css")?.toExternalForm())
            stage.setOnCloseRequest { trigger.trigger() }
            stage.title = "Profile manager"
            stage.icons.add(
                Image(
                    Objects.requireNonNull(
                        PlatformWindow::class.java.getResourceAsStream("/com/bgconsole/platform/ui/img/logo.png")
                    )
                )
            )
            stage.scene = scene
            stage.show()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}