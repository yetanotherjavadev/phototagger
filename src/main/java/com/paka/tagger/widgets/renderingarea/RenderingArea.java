package com.paka.tagger.widgets.renderingarea;

import com.paka.tagger.state.AppState;
import com.paka.tagger.widgets.filebrowser.items.FilePathTreeItem;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class RenderingArea extends FlowPane {

    private static final double IMG_WIDTH = 760;
    private static final double IMG_HEIGHT = 560;

    @FXML // container for image that is currently displayed
    private ImageView imageView;

    @FXML // current path
    private Label selectedItemLabel;

    public RenderingArea() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/RenderingArea.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }

    @FXML
    public void initialize() {
        bind();
    }

    private void bind() {
        AppState.get().getSelectedNodeProperty().addListener((observable, oldValue, newValue) -> {
            selectedItemLabel.setText(newValue == null ? "null" : newValue.getValue().getPathItem().getFullPath().toString());
            imageView.setImage(getImageFrom(newValue));
        });
    }

    private Image getImageFrom(FilePathTreeItem selected) {
        if (selected == null) return null;
        File file = selected.getValue().getPathItem().getFullPath().toFile();
        return new Image(file.toURI().toString(), IMG_WIDTH, IMG_HEIGHT, true, false);
    }
}