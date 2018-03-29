package com.paka.tagger.widgets.filebrowser.items;

import com.paka.tagger.common.graphics.IconProvider;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.nio.file.Files;

import static com.paka.tagger.common.constants.Icons.FILE_ICON_PATH;
import static com.paka.tagger.common.constants.Icons.FOLDER_COLLAPSE_ICON_PATH;

public class FilePathTreeItem extends TreeItem<PathItem> {

	public FilePathTreeItem(PathItem value) {
		this(value, null);
	}

	public FilePathTreeItem(PathItem value, Node graphic) {
		super(value, graphic);
		init(value);
	}

	private void init(PathItem path) {
		if (Files.isDirectory(path.getFullPath())) {
			this.setGraphic(new ImageView(IconProvider.getImage(FOLDER_COLLAPSE_ICON_PATH)));
		} else {
			this.setGraphic(new ImageView(IconProvider.getImage(FILE_ICON_PATH)));
			//TODO: add different icons for different file types
		}
	}
}
