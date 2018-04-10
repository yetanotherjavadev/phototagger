package com.paka.tagger.state;

import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppState {

    //TODO all observables should go here
    private SimpleObjectProperty<FilePathTreeItem> selectedItem = new SimpleObjectProperty<>();
    private SimpleObjectProperty<AppSettings> appSettings = new SimpleObjectProperty<>();

    private static final AppState INSTANCE = new AppState();

    private AppState() {}

    public static AppState get() {
        return INSTANCE;
    }
}
