package com.paka.tagger.widgets.tagspanel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class TagWidget extends HBox {

    private Pane parent;
    private Label label;
    private Button closeBtn;

    public TagWidget(Pane parent, String text) {
        this(parent, text, false);
    }

    public TagWidget(Pane parent, String text, boolean readonly) {
        this.label = new Label(text);
        this.label.setStyle("-fx-text-fill: white");
        this.closeBtn = new Button("X");
        this.parent = parent;
        this.getChildren().addAll(label, closeBtn);
        this.getStyleClass().add("tag");
        if (readonly) {
            closeBtn.setVisible(false);
        } else {
            addClickHandler(null);
        }
    }

    private void addClickHandler(Callback<String, String> callback) { //TODO: make it real
        //wrapping callback
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("TagWidget was removed: " + label.getText());
            parent.getChildren().remove(TagWidget.this);
            if (callback != null) {
                callback.call(event.getButton().toString());
            }
        });
    }
}
