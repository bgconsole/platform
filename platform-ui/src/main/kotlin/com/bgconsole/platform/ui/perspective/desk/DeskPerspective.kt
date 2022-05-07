package com.bgconsole.platform.ui.perspective.desk

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.Perspective
import com.bgconsole.platform.ui.SimpleTrigger
import com.bgconsole.platform.ui.perspective.desk.profile.ProfileWindow
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Node
import javafx.scene.Parent
import javafx.scene.control.Menu
import javafx.scene.control.MenuItem

class DeskPerspective : Perspective() {

    private lateinit var store: Store
    private lateinit var node: Node
    private lateinit var controller: WorkspacePerspectiveController

    private var profileWindow: ProfileWindow? = null

    private val menus: List<Menu> = createMenu()

    override fun getId(): String = "com.bgconsole.platform.perspective.desk"
    override fun getName(): String = "Desk"

    override fun getNode(): Node {
        return node
    }

    override fun setStore(store: Store) {
        this.store = store
    }

    override fun onInit() {
        val resource =
            javaClass.getResource("/com/bgconsole/platform/ui/perspective/desk/desk_perspective.fxml")
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

    override fun getMenus(): List<Menu> {
        return menus
    }

    private fun createMenu(): List<Menu> {
        val menuWorkspace = Menu("Desk")
        val profileMenuItem = MenuItem("Profile manager...")
        profileMenuItem.onAction = EventHandler { openProfileManager() }
        menuWorkspace.items.add(profileMenuItem)
        return listOf(menuWorkspace)
    }

    private fun openProfileManager() {
        if (profileWindow == null) {
            profileWindow = ProfileWindow(store, object : SimpleTrigger {
                override fun trigger() {
                    profileWindow = null
                }
            })
        } else {
            profileWindow?.popUp()
        }
    }

}