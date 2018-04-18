package com.paka.tagger.widgets.filebrowser.items;

import static com.paka.tagger.common.constants.IconPaths.FOLDER_COLLAPSE_ICON_PATH;

import com.paka.tagger.common.graphics.IconProvider;
import com.paka.tagger.model.TreeEntity;
import java.nio.file.Files;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FilePathTreeItem extends FilterableTreeItem<TreeEntity> {

    private boolean scanned;

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
