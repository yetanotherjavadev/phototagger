package com.paka.tagger.widgets.renderingarea;

import java.io.File;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class RenderingArea extends FlowPane {

    // image that is currently displayed
    private ImageView imageView;

    public RenderingArea() {
        createChildren();
    }

    //TODO move to fxml
    private void createChildren() {
        this.imageView = new ImageView();

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(400);
        imageView.setFitHeight(300);
        imageView.setViewport(new Rectangle2D(0,0,400,300));

        getChildren().add(imageView);
        setImage("d:/!images/tomcat.png");
    }

    public void setImage(String url) {
        File file = new File(url);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
    }
}
