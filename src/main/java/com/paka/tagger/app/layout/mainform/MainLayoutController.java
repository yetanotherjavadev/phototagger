package com.paka.tagger.app.layout.mainform;

import com.paka.tagger.widgets.filebrowser.FileBrowser;
import com.paka.tagger.widgets.infopanel.InfoPanel;
import com.paka.tagger.widgets.renderingarea.RenderingArea;
import com.paka.tagger.widgets.tagspanel.TagsPanel;
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
    @FXML private InfoPanel infoPanel;
    @FXML private TagsPanel tagsPanel;

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

    @FXML
    private void init() {
        initLayouts();
    }

    public StackPane getRoot() {
        return root;
    }

    private void initLayouts() {
        initFileBrowser();
        initCentralArea();
        initInfoPanel();
        initTagsPanel();
    }

    private void initTagsPanel() {
        tagsPanel.setTags(null);
    }

    private void initInfoPanel() {
        System.out.println("info panel initialized");
    }

    private void initCentralArea() {
        renderingArea.setBackground(new Background(new BackgroundFill(new Color(.7d, .7d, .7d, 1d),
                new CornerRadii(4d), new Insets(10, 10, 10, 10))));
    }

    private void initFileBrowser() {
        fileBrowser.setImgClickCallBack(param -> {
            System.out.println("clicked on supported file: " + param.toString());
            renderingArea.setImage(param.toString());
            infoPanel.showDataFor(param.toString());
            return null;
        });
    }
}
