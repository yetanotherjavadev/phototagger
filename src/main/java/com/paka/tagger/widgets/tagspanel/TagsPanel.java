package com.paka.tagger.widgets.tagspanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.FlowPane;

public class TagsPanel extends FlowPane {

    private List<TagWidget> tags = new ArrayList<>();

    public TagsPanel() {   }

    public void setTags(List<TagWidget> tags) {
        if (tags == null) tags = getDefaultTags();
        this.tags = tags;
        setPrefHeight(100);
        setMaxHeight(100);
        render();
    }

    private List<TagWidget> getDefaultTags() {
        List<TagWidget> tags = new ArrayList<>(Arrays.asList(new TagWidget(this, "jpg"),
                new TagWidget(this, "png"),
                new TagWidget(this, "bmp"),
                new TagWidget(this, "gif")));
        return tags;
    }

    private void render() {
        this.getChildren().clear();
        this.getChildren().addAll(tags);
    }
}
