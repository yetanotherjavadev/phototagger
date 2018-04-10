package com.paka.tagger.app.layout.mainform;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PhotoTagger extends Application {

    private static final String WINDOW_TITLE = "Photo tagger proto 0.0.1";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.setScene(createScene());
        primaryStage.show();
        //TODO: create "rubber" layout
        primaryStage.setResizable(false);
    }

    private Scene createScene() {
        MainLayoutController mlc = new MainLayoutController();
        Scene scene = new Scene(mlc, 1280, 720);
        scene.getStylesheets().add("/css/mainlayout.css");
        return scene;
    }
}
