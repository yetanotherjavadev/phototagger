package com.paka.tagger.widgets.popup.basic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

//TODO check if it works
public class BasicInfoPopup extends Popup {

    public BasicInfoPopup() {

        Stage stage = new Stage();
        stage.setScene(createScene());
        stage.setTitle("Basic modal popup");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.show();
    }

    private Scene createScene() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/BasicInfoPopup.fxml"));
        fxmlLoader.setController(this);
        Scene scene = new Scene(fxmlLoader.getRoot(), 1280, 720);
        return scene;
    }

    @Override
    public void show() {
        super.show();
    }
}
