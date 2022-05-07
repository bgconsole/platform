package com.bgconsole.platform.store

interface Reducer<T> {

    fun getKey(): String

    fun reduce(store: Store, action: Action): T
}