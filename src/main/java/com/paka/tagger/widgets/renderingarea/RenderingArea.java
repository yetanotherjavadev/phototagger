package com.paka.tagger.widgets.renderingarea;

import com.paka.tagger.state.AppState;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import java.io.File;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class RenderingArea extends FlowPane {

    private static final double IMG_WIDTH = 760;
    private static final double IMG_HEIGHT = 560;

    @FXML // image that is currently displayed
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
        bindAll();
    }

    private void bindAll() {
        SimpleObjectProperty<PathItem> selected = AppState.get().getSelectedItem();

        selectedItemLabel.textProperty().bind(Bindings.createStringBinding(
                () -> selected.isNull().get() ? "null" : selected.get().getFullPath().toString(), selected));

        imageView.imageProperty().bind(Bindings.createObjectBinding(
                () -> getImageFrom(selected), selected));
    }

    private Image getImageFrom(SimpleObjectProperty<PathItem> selected) {
        if (selected.isNull().get()) return null;
        File file = selected.getValue().getFullPath().toFile();
        return new Image(file.toURI().toString(), IMG_WIDTH, IMG_HEIGHT, true, false);
    }
}