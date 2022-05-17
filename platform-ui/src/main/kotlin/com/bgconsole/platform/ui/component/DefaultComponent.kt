package com.bgconsole.platform.ui.component

import com.bgconsole.platform.store.Store
import javafx.scene.Node
import javafx.scene.control.Menu
import javafx.scene.text.Text

class DefaultComponent : Component() {
    override fun getId(): String = "platform.component.default"

    override fun getName(): String = "Default component"

    override fun getNode(): Node = Text("Default component")

    override fun setStore(store: Store) {
    }

    override fun onInit() {
    }

    override fun onClose() {
    }

    override fun onGetVisibility() {
    }

    override fun onLoseVisibility() {
    }

    override fun getMenus(): List<Menu> {
        return emptyList()
    }
}