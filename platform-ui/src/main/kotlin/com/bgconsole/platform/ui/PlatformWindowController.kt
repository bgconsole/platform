package com.bgconsole.platform.ui

import com.bgconsole.platform.store.Store
import com.bgconsole.platform.store.Subscriber
import com.bgconsole.platform.ui.perspective.PLATFORM_PERSPECTIVE
import com.bgconsole.platform.ui.perspective.PerspectiveContent
import com.bgconsole.platform.ui.perspective.PerspectiveRedux
import com.bgconsole.platform.ui.project.*
import javafx.application.HostServices
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.layout.StackPane
import javafx.stage.Stage
import kotlin.system.exitProcess

class PlatformWindowController {

    @FXML
    private lateinit var appContent: StackPane

    @FXML
    private lateinit var menuBar: MenuBar

    @FXML
    private lateinit var perspectiveMenu: Menu

    @FXML
    private lateinit var projectMenu: Menu

    private lateinit var perspectiveContent: PerspectiveContent

    private lateinit var projectTypeContent: ProjectTypeContent

    private lateinit var projectPerspectiveContent: ProjectPerspectiveContent

    private lateinit var store: Store

    private lateinit var hostServices: HostServices

    private lateinit var stage: Stage

    private lateinit var title: String

    fun setStore(store: Store) {
        this.store = store

        val newPerspective = store.get(PLATFORM_PERSPECTIVE) as PerspectiveContent
        updatePerspective(newPerspective)
        store.subscribe(PLATFORM_PERSPECTIVE, object : Subscriber {
            override fun update(entity: Any) {
                updatePerspective(entity as PerspectiveContent)
                updatePerspectiveMenu()
            }
        })

        projectTypeContent = store.get(PLATFORM_PROJECT_TYPE) as ProjectTypeContent
        store.subscribe(PLATFORM_PROJECT_TYPE, object : Subscriber {
            override fun update(entity: Any) {
                projectTypeContent = entity as ProjectTypeContent
                updateProjectMenu()
            }
        })

        projectPerspectiveContent = store.get(PLATFORM_PERSPECTIVE_PROJECT) as ProjectPerspectiveContent
        updateProjectMenu()
        store.subscribe(PLATFORM_PERSPECTIVE_PROJECT, object : Subscriber {
            override fun update(entity: Any) {
                projectPerspectiveContent = entity as ProjectPerspectiveContent
                updateProjectMenu()
            }
        })
    }

    fun setHostServices(hostServices: HostServices?) {
        this.hostServices = hostServices!!
    }

    fun setStage(stage: Stage) {
        this.stage = stage
        this.title = stage.title
    }

    private fun updatePerspective(newPerspective: PerspectiveContent) {
        if (newPerspective.perspectiveStack.isNotEmpty()) {
            if (perspectiveContent.perspectiveStack.isNotEmpty()) {
                val current = perspectiveContent.perspectiveStack.first()
                if (current.getId() != newPerspective.perspectiveStack.first().getId()) {
                    appContent.children.removeFirst()
                    updatePerspective(current, newPerspective.perspectiveStack.first())
                }
            } else {
                updatePerspective(null, newPerspective.perspectiveStack.first())
            }
        }
        perspectiveContent = newPerspective
    }

    private fun updatePerspective(oldPerspective: Perspective?, newPerspective: Perspective) {
        appContent.children.add(newPerspective.getNode())
        stage.title = title + " | " + newPerspective.getName()
        updateDedicatedMenus(oldPerspective, newPerspective)
    }

    private fun updateDedicatedMenus(oldPerspective: Perspective?, newPerspective: Perspective) {
        if (oldPerspective?.getMenus()?.isNotEmpty() == true) {
            val total = oldPerspective.getMenus().size
            for (i in 1..total) {
                menuBar.menus.removeAt(1)
            }
        }
        if (newPerspective.getMenus().isNotEmpty()) {
            val menus = newPerspective.getMenus()
            menuBar.menus.addAll(1, menus)
        }
    }

    private fun updateProjectMenu() {
        projectMenu.items.clear()
        projectTypeContent.types.forEach { (_, projectType) ->
            run {
                val menu = Menu(projectType.getProjectName())
                updateSubProjectMenu(menu, projectType)
                projectMenu.items.add(menu)
            }
        }
    }

    private fun updateSubProjectMenu(menu: Menu, projectType: ProjectType) {
        projectPerspectiveContent.projects.forEach { (_, projectContent) ->
            run {
                if (projectContent.getProjectType() == projectType.getProjectType()) {
                    val menuItem = MenuItem(projectContent.getName())
                    menuItem.onAction = EventHandler {
                        store.dispatch(ProjectPerspectiveRedux.SwitchProject(projectContent.getProjectId()))
                    }
                    menu.items.add(menuItem)
                }
            }
        }
    }

    private fun updatePerspectiveMenu() {
        perspectiveMenu.items.clear()
        if (perspectiveContent.perspectives.isNotEmpty()) {
            perspectiveContent.perspectives.forEach { (key, perspective) ->
                run {
                    if (perspective !is ProjectPerspective) {
                        val menuItem = MenuItem(perspective.getName())
                        menuItem.onAction = EventHandler {
                            store.dispatch(PerspectiveRedux.SwitchPerspective(perspective.getId()))
                        }
                        perspectiveMenu.items.add(menuItem)
                    }
                }
            }
        }
    }

    fun quit(actionEvent: ActionEvent) {
        exitProcess(0)
    }

    fun newWorkspace(actionEvent: ActionEvent) {

    }

    fun openWorkspace(actionEvent: ActionEvent) {

    }

    fun removeWorkspace(actionEvent: ActionEvent) {

    }

    fun createProject(actionEvent: ActionEvent) {

    }

    fun help(actionEvent: ActionEvent) {
        hostServices.showDocument(Hyperlink("https://bgconsole.com/docs/").text)
    }

    fun about(actionEvent: ActionEvent) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "About BG Console"
        alert.headerText = "BG Console version: 2.0"
        alert.contentText = "More info: https://bgconsole.com"
        alert.showAndWait()
    }


}