package com.paka.tagger.widgets.tagspanel;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class Tag extends HBox {

    private FlowPane parent;
    private Label label;
    private Button closeBtn;

    public Tag(FlowPane parent, String text) {
        this.label = new Label(text);
        this.label.setStyle("-fx-text-fill: white");
        this.closeBtn = new Button("X");
        this.parent = parent;
        this.getChildren().addAll(label, closeBtn);
        this.getStyleClass().add("tag");
        addClickHandler(null);
    }

    private void addClickHandler(Callback<String, String> callback) {
        //wrapping callback
        closeBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Tag was removed: " + label.getText());
            parent.getChildren().remove(Tag.this);
            if (callback != null) {
                callback.call(event.getButton().toString());
            }
        });
    }
}
