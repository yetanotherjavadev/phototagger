package com.paka.tagger.widgets.filebrowser;

import com.paka.tagger.common.graphics.IconProvider;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.paka.tagger.common.constants.Icons.FILE_ICON_PATH;
import static com.paka.tagger.common.constants.Icons.FOLDER_COLLAPSE_ICON_PATH;

public class FilePathItem extends TreeItem<Path> {

    public FilePathItem(Path value) {
        this(value, null);
    }

    public FilePathItem(Path value, Node graphic) {
        super(value, graphic);
        init(value);
    }

    private void init(Path path) {
        //test if this is a directory and set the icon
        if (Files.isDirectory(path)) {
//            this.isDirectory = true;
            this.setGraphic(new ImageView(IconProvider.getImage(FOLDER_COLLAPSE_ICON_PATH)));
        } else {
//            this.isDirectory = false;
            this.setGraphic(new ImageView(IconProvider.getImage(FILE_ICON_PATH)));
            //if you want different icons for different file types this is where you'd do it
        }

        //set the value
        if (!getValue().toString().endsWith(File.separator)) {
            //set the value (which is what is displayed in the tree)
            String value = path.toString();
            int indexOf = value.lastIndexOf(File.separator);
            if (indexOf > 0) {
                this.setValue(Paths.get(value.substring(indexOf + 1)));
            } else {
                this.setValue(path);
            }
        }
    }
}
