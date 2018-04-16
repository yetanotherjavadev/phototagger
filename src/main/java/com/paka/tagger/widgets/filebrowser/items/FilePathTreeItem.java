package com.paka.tagger.widgets.filebrowser.items;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.model.TreeEntity;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.nio.file.Files;

import static com.paka.tagger.common.constants.IconPaths.FOLDER_COLLAPSE_ICON_PATH;

public class FilePathTreeItem extends TreeItem<TreeEntity> {

	public FilePathTreeItem(TreeEntity value) {
		this(value, null);
		init(value);
	}

	public FilePathTreeItem(TreeEntity value, Node graphic) {
		super(value, graphic);
	}

	private void init(TreeEntity entity) {
		if (Files.isDirectory(entity.getPathItem().getFullPath())) {
			this.setGraphic(new ImageView(IconProvider.getImage(FOLDER_COLLAPSE_ICON_PATH)));
		} else {
			Image img = IconProvider.getImage(entity.getPathItem().getFullPath().toString());
			this.setGraphic(new ImageView(img));
		}
	}
}
