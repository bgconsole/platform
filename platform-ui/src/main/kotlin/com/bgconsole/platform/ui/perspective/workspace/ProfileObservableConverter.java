package com.bgconsole.platform.ui.perspective.workspace;

import com.bgconsole.platform.domain.Profile;
import javafx.collections.ObservableList;
import javafx.util.StringConverter;

public class ProfileObservableConverter extends StringConverter<Profile> {

    private final ObservableList<Profile> profileList;

    public ProfileObservableConverter(ObservableList<Profile> profileList) {
        this.profileList = profileList;
    }

    @Override
    public String toString(Profile profile) {
        return profile != null ? profile.getName() : null;
    }

    @Override
    public Profile fromString(String s) {
        return profileList.stream().filter(profile -> profile.getName().equals(s)).findFirst().orElse(null);
    }
}
