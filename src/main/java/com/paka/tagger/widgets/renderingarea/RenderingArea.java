package com.paka.tagger.widgets.renderingarea;

import java.io.File;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class RenderingArea extends FlowPane {

    // image that is currently displayed
    private ImageView imageView;
    private final double IMG_WIDTH = 400;
    private final double IMG_HEIGHT = 300;

    public RenderingArea() {
        createChildren();
    }

    //TODO move to fxml
    private void createChildren() {
        this.imageView = new ImageView();

        imageView.setPreserveRatio(true);
        imageView.setFitWidth(IMG_WIDTH);
        imageView.setFitHeight(IMG_HEIGHT);
        imageView.setViewport(new Rectangle2D(0,0,IMG_WIDTH,IMG_HEIGHT));

        getChildren().add(imageView);
        setImage("d:/!images/tomcat.png");
    }

    public void setImage(String url) {
        File file = new File(url);
        Image image = new Image(file.toURI().toString(), IMG_WIDTH, IMG_HEIGHT, true, false);
        imageView.setImage(image);
    }
}
