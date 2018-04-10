package com.paka.tagger.widgets.renderingarea;

import com.paka.tagger.state.AppState;
import java.io.File;
import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class RenderingArea extends FlowPane {

    @FXML // image that is currently displayed
    private ImageView imageView;

    @FXML // current path
    private Label currentlySelectedLabel;

    public RenderingArea() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/RenderingArea.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }

    @FXML
    public void init() {
        setImage("d:/!images/tomcat.png");
        bindAll();
    }

    private void bindAll() { //how do I get real observable here?
        currentlySelectedLabel.textProperty().bind(Bindings.concat(AppState.get().getSelectedItem().getValue().getValue().getFullPath()));

    }

    public void setImage(String url) {
        File file = new File(url);
        Image image = new Image(file.toURI().toString(), 400, 300, true, false);
        imageView.setImage(image);
    }
}