package com.bgconsole.platform.ui

import com.bgconsole.platform.store.Store
import javafx.scene.Node
import java.util.*

abstract class Perspective {

    val id = UUID.randomUUID().toString()

    abstract fun getNode(): Node

    abstract fun setStore(store: Store)
}