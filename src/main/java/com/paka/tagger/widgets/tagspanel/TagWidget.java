package com.paka.tagger.widgets.tagspanel;

import com.paka.tagger.common.model.Tag;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

//TODO @FXML
public class TagWidget extends HBox {

    private Pane parent;
    private Tag tag;
    private Button closeBtn;

    public TagWidget(Pane parent, Tag tag) {
        this(parent, tag, false);
    }

    public TagWidget(Pane parent, Tag tag, boolean readonly) {
        Label label = new Label(tag.getText());
        label.getStyleClass().add("tagLabel");
        this.tag = tag;
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
            System.out.println("TagWidget was removed: " + tag.getText());
            parent.getChildren().remove(TagWidget.this);
            ((TagsPanel) parent).removeTag(tag);
            if (callback != null) {
                callback.call(event.getButton().toString());
            }
        });
    }
}
