package com.paka.tagger.widgets.filebrowser.items;

import com.paka.tagger.common.graphics.IconProvider;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Files;
import javax.swing.*;

import static com.paka.tagger.common.constants.IconPaths.FILE_ICON_PATH;
import static com.paka.tagger.common.constants.IconPaths.FOLDER_COLLAPSE_ICON_PATH;

public class FilePathTreeItem extends TreeItem<PathItem> {

	public FilePathTreeItem(PathItem value) {
		this(value, null);
		init(value);
	}

	public FilePathTreeItem(PathItem value, Node graphic) {
		super(value, graphic);
	}

	private void init(PathItem path) {
		if (Files.isDirectory(path.getFullPath())) {
			this.setGraphic(new ImageView(IconProvider.getImage(FOLDER_COLLAPSE_ICON_PATH)));
		} else {
			Image img = IconProvider.getImage(path.getFullPath().toString());
			this.setGraphic(new ImageView(img));
		}
	}
}
