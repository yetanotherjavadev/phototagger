package com.paka.tagger.state;

import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.filters.TagFilter;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;

@Getter
public class AppState {

    //TODO all observables should go here
    private SimpleObjectProperty<TreeEntity> selectedItemProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<FilePathTreeItem> selectedNodeProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<AppSettings> appSettingsProperty = new SimpleObjectProperty<>();
    private SimpleObjectProperty<TagFilter> appliedFiltersProperty = new SimpleObjectProperty<>();

    private static final AppState INSTANCE = new AppState();

    private AppState() {
        setAppSettings(new AppSettings());
    }

    public static AppState get() {
        return INSTANCE;
    }

    public void setSelectedItem(TreeEntity pathItem) {
        this.selectedItemProperty.setValue(pathItem);
    }

    public void setAppSettings(AppSettings appSettings) {
        this.appSettingsProperty.setValue(appSettings);
    }

    public void setAppliedFilters(TagFilter tagFilter) {
        this.appliedFiltersProperty.setValue(tagFilter);
    }

    public void setSelectedNode(FilePathTreeItem node) {
        this.selectedNodeProperty.setValue(node);
    }
}
