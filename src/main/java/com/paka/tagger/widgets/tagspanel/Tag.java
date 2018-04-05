package com.paka.tagger.widgets.tagspanel;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

public class Tag extends Label {

    private String text;
    private FlowPane parent;

    public Tag(FlowPane parent, String text) {
        this.parent = parent;
        this.text = text;
        //TODO: add "tagish" styling
//        this.getStyleClass().add();
        addClickHandler();
    }

    private void addClickHandler() {
        System.out.println("Tag was removed: " + text);
        parent.getChildren().remove(this);
    }

}
