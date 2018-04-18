package com.paka.tagger.widgets.filebrowser.items;

import javafx.scene.control.TreeItem;

@FunctionalInterface
public interface TreeItemPredicate<T> {

    boolean test(TreeItem<T> parent, T value);
}