package com.paka.tagger.app.layout.menu;

import java.io.IOException;
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
        if (tagCallback != null) {
            String tag = tagCallback.call(null);
            System.out.println(tag);
        }
    }

    @FXML
    public void removeAllTags() {
        if (tagRemoveCallback != null) {
            String result = tagRemoveCallback.call("aaa");
            System.out.println(result);
        }
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
