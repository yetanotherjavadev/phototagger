package com.paka.tagger.state;

import com.paka.tagger.state.filters.Filter;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

@Getter
public class AppState {

    //TODO all observables should go here
    private SimpleObjectProperty<PathItem> selectedItem = new SimpleObjectProperty<>();
    private SimpleObjectProperty<AppSettings> appSettings = new SimpleObjectProperty<>();
    private SimpleObjectProperty<List<Filter>> appliedFilters = new SimpleObjectProperty<>();

    private static final AppState INSTANCE = new AppState();

    private AppState() {}

    public static AppState get() {
        return INSTANCE;
    }

    public void setSelectedItem(PathItem pathItem) {
        this.selectedItem.setValue(pathItem);
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings.setValue(appSettings);
    }

    public void setAppliedFilters(List<Filter> appliedFilters) {
        this.appliedFilters.setValue(appliedFilters);
    }
}
