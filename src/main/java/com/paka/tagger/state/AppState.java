package com.paka.tagger.state;

import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.filters.Filter;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

@Getter
public class AppState {

    //TODO all observables should go here
    private SimpleObjectProperty<TreeEntity> selectedItem = new SimpleObjectProperty<>();
    private SimpleObjectProperty<AppSettings> appSettings = new SimpleObjectProperty<>();
    private SimpleObjectProperty<List<Filter>> appliedFilters = new SimpleObjectProperty<>();

    private static final AppState INSTANCE = new AppState();

    private AppState() {}

    public static AppState get() {
        return INSTANCE;
    }

    public void setSelectedItem(TreeEntity pathItem) {
        this.selectedItem.setValue(pathItem);
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettings.setValue(appSettings);
    }

    public void setAppliedFilters(List<Filter> appliedFilters) {
        this.appliedFilters.setValue(appliedFilters);
    }
}
