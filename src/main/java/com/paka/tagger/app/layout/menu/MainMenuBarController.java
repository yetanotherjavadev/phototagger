package com.paka.tagger.app.layout.menu;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.TagFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.util.Callback;

public class MainMenuBarController extends MenuBar {

    private Callback<String, String> scanCallback;
    private Callback<String, String> tagCallback;
    private Callback<String, String> tagRemoveCallback;

    public MainMenuBarController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/MainMenuBar.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        fxmlLoader.load();
    }

    @FXML
    public void exitAction() {
        System.exit(0);
    }

    @FXML
    public void performScan() {
        if (scanCallback != null) {
            String scan = scanCallback.call(null);
            System.out.println(scan);
        }
    }

    @FXML
    public void testJPGTag() {
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("jpg"));
        TagFilter tf = new TagFilter(tags);

        AppState.get().setAppliedFilters(tf);
    }

    @FXML
    public void removeAllTags() {
        AppState.get().setAppliedFilters(new TagFilter());
    }

    public void setScanCallback(Callback<String, String> callback) {
        this.scanCallback = callback;
    }

    public void setTagCallback(Callback<String, String> callback) {
        this.tagCallback = callback;
    }

    public void setRemoveTagCallback(Callback<String, String> callback) {
        this.tagRemoveCallback = callback;
    }
}
