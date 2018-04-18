package com.paka.tagger.widgets.filebrowser.items;

import java.lang.reflect.Field;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;

public class FilterableTreeItem<T> extends TreeItem<T> {
    private final ObservableList<TreeItem<T>> sourceList;
    private FilteredList<TreeItem<T>> filteredList;
    private ObjectProperty<TreeItemPredicate<T>> predicate = new SimpleObjectProperty<>();

    public FilterableTreeItem(T value, Node graphics) {
        super(value, graphics);
        this.sourceList = FXCollections.observableArrayList();
        this.filteredList = new FilteredList<>(this.sourceList);

        this.filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> child -> {
            // Set the predicate of child items to force filtering
            FilterableTreeItem<T> filterableChild = (FilterableTreeItem<T>) child;
            filterableChild.setPredicate(this.predicate.get());

            if (this.predicate.get() == null || child.getChildren().size() > 0)
                return true;

            return this.predicate.get().test(this, child.getValue());
        }, this.predicate));
        setHiddenFieldChildren(this.filteredList);
    }

    @SuppressWarnings("unchecked")
    private void setHiddenFieldChildren(ObservableList<TreeItem<T>> list) {
        try { //TODO: remove reflection from here, javafx 8 should allow this
            Field childrenField = TreeItem.class.getDeclaredField("children"); //$NON-NLS-1$
            childrenField.setAccessible(true);
            childrenField.set(this, list);

            Field childrenListenerField = TreeItem.class.getDeclaredField("childrenListener"); //$NON-NLS-1$
            childrenListenerField.setAccessible(true);
            list.addListener((ListChangeListener<? super TreeItem<T>>) childrenListenerField.get(this));
        } catch (ReflectiveOperationException | RuntimeException e) {
            throw new RuntimeException("Could not set TreeItem.children", e); //$NON-NLS-1$
        }
    }

    public ObservableList<TreeItem<T>> getInternalChildren() {
        return this.sourceList;
    }

    public void setPredicate(TreeItemPredicate<T> predicate) {
        this.predicate.setValue(predicate);
    }
}