package com.bgconsole.platform.ui.component

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.ui.Perspective
import javafx.scene.Node
import javafx.scene.control.Menu

const val PLATFORM_COMPONENT_ID = "platform.component"

class ComponentPerspective : Perspective() {

    private var component: Component = DefaultComponent()

    fun setComponent(component: Component) {
        this.component = component
    }

    override fun getId(): String = PLATFORM_COMPONENT_ID

    override fun getName(): String {
        return component.getName()
    }

    override fun getNode(): Node {
        return component.getNode()
    }

    override fun setStore(store: Store) {
        component.setStore(store)
    }

    override fun onInit() {
        component.onInit()
    }

    override fun onClose() {
        component.onClose()
    }

    override fun onGetVisibility() {
        component.onGetVisibility()
    }

    override fun onLoseVisibility() {
        component.onLoseVisibility()
    }

    override fun getMenus(): List<Menu> {
        return component.getMenus()
    }
}