package com.paka.tagger.app.layout.mainform;

import com.paka.tagger.app.layout.menu.MainMenuBarController;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class MainLayoutController extends StackPane {

    @FXML private MainMenuBarController mainMenu;
    @FXML private BorderPane mainPane;
    @FXML private FileBrowser fileBrowser;
    @FXML private RenderingArea renderingArea;
    @FXML private InfoPanel infoPanel;
    @FXML private TagsPanel tagsPanel;

    public MainLayoutController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/MainLayout.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        init();
    }

    @FXML
    private void init() {
        initLayouts();
        mainPane.getStyleClass().add("mainPane");
    }

    private void initLayouts() {
        initMenu();
        initCentralArea();
        initInfoPanel();
        initTagsPanel();
    }

    private void initMenu() {
        //should scan directories for images
        mainMenu.setScanCallback(param -> null);
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
}
