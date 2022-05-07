package com.bgconsole.platform.ui

import com.bgconsole.platform.store.Store
import javafx.scene.Node
import javafx.scene.control.Menu

abstract class Perspective {

    abstract fun getId(): String

    abstract fun getName(): String

    abstract fun getNode(): Node

    abstract fun setStore(store: Store)

    abstract fun onInit()

    abstract fun onClose()

    abstract fun onGetVisibility()

    abstract fun onLoseVisibility()

    abstract fun getMenus(): List<Menu>
}