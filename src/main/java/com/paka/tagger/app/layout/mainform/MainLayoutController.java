package com.paka.tagger.app.layout.mainform;

import com.paka.tagger.app.layout.menu.MainMenuBarController;
import com.paka.tagger.common.model.Tag;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.TagFilter;
import com.paka.tagger.widgets.filebrowser.FileBrowser;
import com.paka.tagger.widgets.infopanel.InfoPanel;
import com.paka.tagger.widgets.popup.basic.BasicInfoPopup;
import com.paka.tagger.widgets.renderingarea.RenderingArea;
import com.paka.tagger.widgets.tagspanel.TagsPanel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class MainLayoutController extends StackPane {

    @FXML
    private MainMenuBarController mainMenu;
    @FXML
    private BorderPane mainPane;
    @FXML
    private FileBrowser fileBrowser;
    @FXML
    private RenderingArea renderingArea;
    @FXML
    private InfoPanel infoPanel;
    @FXML
    private TagsPanel tagsPanel;

    @FXML //fxml?
    private BasicInfoPopup infoPopup;

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

    private BasicInfoPopup getInfoPopup() {
        if (infoPopup == null) {
            infoPopup = new BasicInfoPopup();
        }
        return infoPopup;
    }

    private void initLayouts() {
        initMenu();
        initCentralArea();
        initInfoPanel();
        initTagsPanel();
    }

    private void initMenu() {
        //should scan directories for images
        mainMenu.setScanCallback(new Callback<String, String>() {
            @Override
            public String call(String param) {
                fileBrowser.rescanCurrent();
                return "OK";
            }
        });
    }

    private void initTagsPanel() {
        AppState.get().getAppliedFiltersProperty().addListener(new ChangeListener<TagFilter>() {
            @Override
            public void changed(ObservableValue<? extends TagFilter> observable, TagFilter oldValue, TagFilter newValue) {
                List<Tag> newAppliedTags = new ArrayList<>(newValue.getSelectedTags());
                tagsPanel.setTags(newAppliedTags);
            }
        });
    }

    private void initInfoPanel() {
        System.out.println("info panel initialized");
    }

    private void initCentralArea() {
        renderingArea.setBackground(new Background(new BackgroundFill(new Color(.7d, .7d, .7d, 1d),
                new CornerRadii(4d), new Insets(10, 10, 10, 10))));
    }
}
