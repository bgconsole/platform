package com.bgconsole.platform.ui.perspective.desk.profile

import com.bgconsole.platform.domain.Profile
import com.bgconsole.platform.engine.profile.ENGINE_USER_SESSION_PROFILE
import com.bgconsole.platform.engine.profile.ProfileRedux
import com.bgconsole.platform.engine.profile.SaveProfile
import com.bgconsole.platform.store.Store
import com.bgconsole.platform.store.Subscriber
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.*
import java.util.function.Consumer

class ProfileWindowController {

    @FXML
    private lateinit var profileList: ListView<Profile>

    private lateinit var store: Store

    fun setStore(store: Store) {
        this.store = store

        val observableList: ObservableList<Profile> = FXCollections.observableArrayList<Profile>()
        profileList.setCellFactory {
            object : ListCell<Profile?>() {
                override fun updateItem(item: Profile?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty || item == null) {
                        null
                    } else {
                        item.name
                    }
                }
            }
        }
        profileList.items = observableList
        store.subscribe(
            ENGINE_USER_SESSION_PROFILE,
            object : Subscriber {
                override fun update(entity: Any) {
                    observableList.setAll(entity as List<Profile>)
                }
            })
        store.dispatch(ProfileRedux.LoadProfiles())
    }

    @FXML
    fun add() {
        val inputDialog = TextInputDialog("New profile")
        inputDialog.contentText = "Name: "
        inputDialog.headerText = "Set the name of the new profile"
        inputDialog.showAndWait().ifPresent(Consumer { newName: String? ->
            val profile = Profile.profile(
                newName!!, ""
            )
            store.dispatch(SaveProfile(profile))
        })
    }

    @FXML
    fun update() {
        val (_, name) = profileList.selectionModel.selectedItem
        val inputDialog = TextInputDialog("Update profile name")
        inputDialog.contentText = "Name: "
        inputDialog.headerText = "Set the new name of the profile"
        inputDialog.editor.text = name
        inputDialog.showAndWait().ifPresent(Consumer { newName: String ->
            if (newName != name) {
//                profile.setName(newName);
//                profileService.save(profile.getId(), profile);
            }
        })
    }

    @FXML
    fun delete() {
        val (_, name) = profileList.selectionModel.selectedItem
        val alert = Alert(Alert.AlertType.CONFIRMATION)
        alert.title = "Delete profile"
        alert.headerText = "The profile \"$name\" is about to be deleted."
        alert.contentText = "Are you sure you would like to delete it ?"
        alert.showAndWait().ifPresent(Consumer { buttonType: ButtonType ->
            if (buttonType == ButtonType.OK) {
//                profileService.delete(profile.getId());
            }
        })
    }
}