package com.paka.tagger.app.layout.mainform;

import com.paka.tagger.widgets.filebrowser.FileBrowser;
import com.paka.tagger.widgets.renderingarea.RenderingArea;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MainLayoutController {

    @FXML private FileBrowser fileBrowser;
    @FXML private RenderingArea renderingArea;
    @FXML public StackPane root;

    public MainLayoutController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainLayout.fxml"));
        fxmlLoader.setController(this);

        try {
            root = fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        init();
    }

    public StackPane getRoot() {
        return root;
    }

    @FXML
    private void init() {
        initLayouts();
    }

    private void initLayouts() {
        initCentralArea();
        initTree();
    }

    private void initCentralArea() {
        renderingArea.setBackground(new Background(new BackgroundFill(new Color(.7d, .7d, .7d, 1d),
                new CornerRadii(4d), new Insets(10, 10, 10, 10))));
    }

    private void initTree() {
        fileBrowser.setImgClickCallBack(param -> {
            System.out.println("clicked on supported file: " + param.toString());
            renderingArea.setImage(param.toString());
            return null;
        });
    }
}
