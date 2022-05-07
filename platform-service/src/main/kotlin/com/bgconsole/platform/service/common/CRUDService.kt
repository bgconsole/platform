package com.bgconsole.platform.service.common

import com.bgconsole.platform.domain.Profile

interface CRUDService<T> {

    fun findById(id: String): T?

    fun findAll(): List<T>

    fun add(t: T)

    fun update(id: String, newT: T): T

    fun delete(id: String)
}