package com.bgconsole.platform.store

class Node<E>(private var entity: E) {

    fun update(entity: Any) {
        this.entity = entity as E
    }

    fun get(): E {
        return entity
    }
}