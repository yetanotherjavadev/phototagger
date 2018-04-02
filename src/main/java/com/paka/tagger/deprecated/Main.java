package com.paka.tagger.deprecated;

import com.paka.tagger.files.FilesManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private ListView<String> list = new ListView<>();

    @Override
    public void start(Stage stage) {
        VBox box = new VBox();
        Scene scene = new Scene(box, 300, 300);
        stage.setScene(scene);
        stage.setTitle("Files List");
        box.getChildren().addAll(list);
        VBox.setVgrow(list, Priority.ALWAYS);

        FilesManager fm = new FilesManager();
        ObservableList<String> data = FXCollections.observableArrayList(fm.getFiles());

        list.setItems(data);

//        list.setCellFactory(list -> new AttachmentListCell());

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}