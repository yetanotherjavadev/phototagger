package com.paka.tagger.state;

import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

@Getter
public class AppStateManager {

    private static final AppStateManager INSTANCE = new AppStateManager();

    private SimpleObjectProperty<AppState> currentState = new SimpleObjectProperty<>();
    private boolean isValid;

    private AppStateManager() {}

    private AppStateManager get() {
        return INSTANCE;
    }
}
