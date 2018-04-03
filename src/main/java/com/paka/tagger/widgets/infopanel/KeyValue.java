package com.paka.tagger.widgets.infopanel;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class KeyValue extends HBox {

    private static final String DEFAULT_NO_VALUE = "-";

    private Label keyLabel;
    private Label valueLabel;

    public KeyValue(String key, String value) {
        this.keyLabel = new Label(key);
        this.valueLabel = new Label(value);
        this.getChildren().addAll(keyLabel, valueLabel);
    }

    public void setValue(Object value) {
        if (value != null) {
            valueLabel.setText(value.toString());
        } else {
            valueLabel.setText(DEFAULT_NO_VALUE);
        }
    }

    public void setKey(String key) {
        keyLabel.setText(key);
    }
}
