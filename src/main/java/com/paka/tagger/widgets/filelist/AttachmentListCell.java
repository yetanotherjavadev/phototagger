package com.paka.tagger.widgets.filelist;

import com.paka.tagger.common.graphics.IconProvider;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AttachmentListCell extends ListCell<String> {

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
            setText(null);
        } else {
            Image fxImage = IconProvider.getImage(item);
            ImageView imageView = new ImageView(fxImage);
            setGraphic(imageView);
            setText(item);
        }
    }

}