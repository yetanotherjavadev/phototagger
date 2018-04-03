package com.paka.tagger.app.layout.mainform;

import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhotoTagger extends Application {

    private static final String WINDOW_TITLE = "Photo tagger proto 0.0.1";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        MainLayoutController mlc = new MainLayoutController();

        Scene scene = new Scene(mlc.getRoot(), 1000, 600);

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setMaxWidth(1000);
        primaryStage.setMinWidth(800);
    }
}
