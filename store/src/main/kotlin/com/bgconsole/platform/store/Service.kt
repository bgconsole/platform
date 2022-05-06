package com.bgconsole.platform.store

interface Service {

    fun getKey(): String

    fun execute(store: Store, action: Action)
}