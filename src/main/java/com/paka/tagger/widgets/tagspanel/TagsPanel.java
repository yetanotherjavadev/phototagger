package com.paka.tagger.widgets.tagspanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.FlowPane;

public class TagsPanel extends FlowPane {

    private List<Tag> tags = new ArrayList<>();

    public TagsPanel() {   }

    public void setTags(List<Tag> tags) {
        if (tags == null) tags = getDefaultTags();
        this.tags = tags;
        setPrefHeight(100);
        setMaxHeight(100);
        render();
    }

    private List<Tag> getDefaultTags() {
        List<Tag> tags = new ArrayList<>(Arrays.asList(new Tag(this, "Italy"),
                new Tag(this, "Spain"),
                new Tag(this, "Romania"),
                new Tag(this, "Ukraine")));
        return tags;
    }

    private void render() {
        this.getChildren().clear();
        this.getChildren().addAll(tags);
    }
}
