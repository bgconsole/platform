package com.bgconsole.platform.ui.perspective.workspace

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.domain.Project
import com.bgconsole.platform.domain.Workspace
import com.bgconsole.platform.engine.profile.ENGINE_USER_SESSION_PROFILE
import com.bgconsole.platform.engine.project.ENGINE_USER_SESSION_PROJECT
import com.bgconsole.platform.engine.workspace.ENGINE_USER_SESSION_WORKSPACE
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.store.Subscriber
import com.bgconsole.platform.ui.project.ProjectTypeRedux
import javafx.application.HostServices
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.stage.DirectoryChooser
import javafx.stage.Stage
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

open class WorkspacePerspectiveController {

    @FXML
    private lateinit var workspaceList: ListView<Workspace>

    @FXML
    private lateinit var profileSelector: ChoiceBox<Profile?>

    @FXML
    private lateinit var projectTable: TableView<Project>

    private var projectObservableList: ObservableList<Project>? = null

    private var stage: Stage? = null

    private lateinit var store: Store

    private var mainWindow: WorkspacePerspectiveContent? = null

    private var hostServices: HostServices? = null

    fun setStore(store: Store) {
        this.store = store
        projectObservableList = FXCollections.observableArrayList()
        val name = TableColumn<Project, String>("Project")
        name.cellValueFactory = PropertyValueFactory("Name")
        val description = TableColumn<Project, String>("Description")
        description.cellValueFactory = PropertyValueFactory("Description")
        projectTable.columns.add(name)
        projectTable.columns.add(description)
        projectTable.setRowFactory { tableView: TableView<Project>? ->
            val row = TableRow<Project>()
            row.onMouseClicked = EventHandler { event: MouseEvent ->
                if (event.clickCount == 2 && !row.isEmpty) {
                    val project = row.item
                    try {
                        //                        AppData.instance.addProject(project);
//                        ProjectWindow(project)
//                        store.dispatch(OpenProject(project))
                        store.dispatch(ProjectTypeRedux.NewInstance(project))
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
            row
        }
        projectTable.columnResizePolicy = TableView.CONSTRAINED_RESIZE_POLICY
        projectTable.items = projectObservableList
        val profileList = FXCollections.observableArrayList<Profile?>()
        //        profileSelector.getSelectionModel().selectFirst();
        profileSelector.converter = ProfileObservableConverter(profileList)
        profileSelector.items = profileList
        store.subscribe(
            ENGINE_USER_SESSION_PROFILE,
            object : Subscriber {
                override fun update(entity: Any) {
                    profileList.setAll(entity as List<Profile?>?)
                }
            })
        profileList.setAll(store.get(ENGINE_USER_SESSION_PROFILE) as List<Profile?>?)
        val workspaceObservableList = FXCollections.observableArrayList<Workspace>()
        workspaceList.setCellFactory {
            object : ListCell<Workspace?>() {
                override fun updateItem(item: Workspace?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty) {
                        null
                    } else {
                        item?.name
                    }
                }
            }
        }
        workspaceList.onMouseClicked = EventHandler { click: MouseEvent? ->
            store.dispatch(
                WorkspacePerspectiveRedux.SelectWorkspace(
                    workspaceList.selectionModel.selectedItem
                )
            )
        }
        workspaceList.items = workspaceObservableList
        store.subscribe(
            ENGINE_USER_SESSION_WORKSPACE,
            object : Subscriber {
                override fun update(entity: Any) {
                    workspaceObservableList.setAll(entity as List<Workspace>?)
                }
            })
        store.subscribe(
            ENGINE_USER_SESSION_PROJECT,
            object : Subscriber {
                override fun update(entity: Any) {
                    projectObservableList?.setAll(entity as List<Project>?)
                }
            })
        mainWindow = store.get(UI_MAIN_WINDOW) as WorkspacePerspectiveContent?
        store.subscribe(UI_MAIN_WINDOW,
            object : Subscriber {
                override fun update(entity: Any) {
                    mainWindow = entity as WorkspacePerspectiveContent?
                }
            })
    }


    fun setHostServices(hostServices: HostServices?) {
        this.hostServices = hostServices
    }

    @FXML
    fun newWorkspace(event: ActionEvent?) {
        if (mainWindow?.selectedProfile != null) {
//            NewLocation(this, mainWindow.selectedProfile)
        }
    }

    @FXML
    fun openWorkspace(event: ActionEvent?) {
        val directoryChooser = DirectoryChooser()
        val selectedDirectory = directoryChooser.showDialog(stage)
        if (selectedDirectory != null) {
            val strPath = selectedDirectory.absolutePath
            val path = Paths.get(strPath)
            if (Files.isDirectory(path)) {
//                try {
//                    Workspace workspace = ParseYAMLFile.readWorkspace(Paths.get(strPath, "workspace.yaml").toString());
//
//                    Location newLocation = new Location(UUID.randomUUID().toString(), workspace.getName(), strPath);
//                    List<Location> locations = new ArrayList<>(selectedProfile.getLocations());
//                    locations.add(newLocation);
//
//                    WriteYAMLFile.writeLocations(locations, ProjectData.DEFAULT_PROFILE_DIR.toString());
//
//                    MainWindowData.instance.reloadLocations();
//                    setProfileList(MainWindowData.instance.getProfiles());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    fun setStage(stage: Stage?) {
        this.stage = stage
    }

    fun changeProfile(event: ActionEvent?) {
        if (profileSelector.value != null) {
            setCurrentProfile(profileSelector.value)
        }
    }

    private fun setCurrentProfile(profile: Profile?) {
        profile?.let { WorkspacePerspectiveRedux.SelectProfile(it) }?.let { store.dispatch(it) }
//        selectedProfile = profile;
////        locationCache = new ArrayList<>(profile.getLocations());
//        locationCache = new ArrayList<>();
//        locationList.getItems().clear();
//        locationCache.forEach(location -> {
//            locationList.getItems().add(location.getName());
//            locationList.setOnMouseClicked(click -> {
//                Location loc = locationCache.get(locationList.getSelectionModel().getSelectedIndex());
//                Workspace workspace = MainWindowData.instance.loadWorkspace(loc);
//                workspace.setPath(loc.getPath());
//                selectedWorkspace = workspace;
//                List<Project> projects = MainWindowData.instance.loadProjects(loc.getPath());
//                projects.forEach(project -> project.setWorkspace(workspace));
//                changeProjectInPane(projects);
////                if (click.getClickCount() == 1) {
////                    Location loc = locationCache.get(locationList.getSelectionModel().getSelectedIndex());
////                    try {
////                        new TerminalWindow(loc);
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                }
//
//            });
//        });
    }

    private fun changeProjectInPane(projects: List<Project>) {
        projectObservableList!!.clear()
        projectObservableList!!.setAll(projects)
    }

    fun openProfileManager(event: ActionEvent?) {
//        if (profileWindow == null) {
//            profileWindow = ProfileWindow(SimpleTrigger { profileWindow = null })
//        } else {
//            profileWindow.popUp()
//        }
    }

    fun about(event: ActionEvent?) {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "About BG Console"
        alert.headerText = "BG Console version: 2.0"
        alert.contentText = "More info: https://bgconsole.com"
        alert.showAndWait()
    }

    fun help(event: ActionEvent?) {
        hostServices!!.showDocument(Hyperlink("https://bgconsole.com/docs/").text)
    }

    fun removeWorkspace(event: ActionEvent?) {}

    fun createProject(event: ActionEvent?) {
        if (mainWindow?.selectedWorkspace != null) {
//            new NewProject(this, mainWindow.getSelectedWorkspace());
        }
    }

}