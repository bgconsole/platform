package com.bgconsole.platform.engine.profile

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.engine.common.YAMLParser
import com.bgconsole.platform.service.profile.ProfileService
import java.nio.file.Files
import java.nio.file.Paths

class CoreProfileServiceImpl : ProfileService {

    private val yamlParser = YAMLParser(Profile.empty(), emptyList())

    private val path = Paths.get(System.getProperty("user.home"), ".com.bgconsole", "profiles")

    override fun findById(id: String): Profile? {
        return findAll().find { profile -> profile.id == id }
    }

    override fun findAll(): List<Profile> {
        return path.toFile().listFiles { _, name -> name.endsWith(".yaml") }?.map {
            yamlParser.readOne(it.absolutePath)
        }.orEmpty()
    }

    override fun add(t: Profile) {
        yamlParser.writeOne(t, Paths.get(path.toString(), t.id + ".yaml").toString())
    }

    override fun update(id: String, newT: Profile): Profile {
        yamlParser.writeOne(newT, Paths.get(path.toString(), "$id.yaml").toString())
        return newT
    }

    override fun delete(id: String) {
        Files.deleteIfExists(Paths.get(path.toString(), "$id.yaml"))
    }
}