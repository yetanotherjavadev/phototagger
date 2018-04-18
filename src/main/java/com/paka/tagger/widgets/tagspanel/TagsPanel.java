package com.paka.tagger.widgets.tagspanel;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.TagFilter;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.FlowPane;

public class TagsPanel extends FlowPane {

    private List<Tag> tags = new ArrayList<>();

    public TagsPanel() {
        setPrefHeight(100);
        setMaxHeight(100);
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
        render();
    }

    private void render() {
        this.getChildren().clear();

        for (Tag tag : tags) {
            TagWidget tagWidget = new TagWidget(this, tag);
            this.getChildren().add(tagWidget);
        }
    }

    void removeTag(Tag tag) {
        this.tags.remove(tag);

        TagFilter newFilter = new TagFilter();
        newFilter.getSelectedTags().addAll(tags);

        AppState.get().setAppliedFilters(newFilter);
    }
}
