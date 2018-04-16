package com.paka.tagger.widgets.infopanel;

import javafx.beans.NamedArg;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class KeyValue extends HBox {

    private static final String DEFAULT_NO_VALUE = "-";

    private Label nameLabel;
    private Label valueLabel;

    public KeyValue(@NamedArg("name") String name, @NamedArg("value") String value) {
        this.nameLabel = new Label(name);
        this.valueLabel = new Label(value);
        this.getChildren().addAll(nameLabel, valueLabel);
    }

    public void setValue(Object value) {
        if (value != null) {
            valueLabel.setText(value.toString());
        } else {
            valueLabel.setText(DEFAULT_NO_VALUE);
        }
    }

    public void setKey(String key) {
        nameLabel.setText(key);
    }
}
