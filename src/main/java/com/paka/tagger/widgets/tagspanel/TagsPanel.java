package com.paka.tagger.widgets.tagspanel;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.filters.TagFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

public class TagsPanel extends FlowPane {

    private List<Tag> tags = new ArrayList<>();

    @FXML
    private TextField tagInput;
    @FXML
    private Button addTagBtn;
    @FXML
    private FlowPane tagsContainer;


    public TagsPanel() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxmls/TagsPanel.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void addTag() { //TODO: implement convenient methods for updating filters
        if (isValidInput(tagInput.getText())) {
            Tag tag = new Tag(tagInput.getText());

            TagFilter currentState = AppState.get().getAppliedFiltersProperty().getValue();

            if (currentState == null || currentState.getSelectedTags().contains(tag)) return;

            Set<Tag> newSet = new HashSet<>(currentState.getSelectedTags());
            newSet.add(tag);

            TagFilter newState = new TagFilter(newSet);

            AppState.get().setAppliedFilters(newState);
        }
    }

    @FXML
    public void setTags(List<Tag> tags) {
        this.tags = tags;
        render();
    }

    private void render() {
        tagsContainer.getChildren().clear();

        for (Tag tag : tags) {
            TagWidget tagWidget = new TagWidget(this, tag);
            tagsContainer.getChildren().add(tagWidget);
        }
    }

    void removeTag(Tag tag) {
        this.tags.remove(tag);

        TagFilter newFilter = new TagFilter();
        newFilter.getSelectedTags().addAll(tags);

        AppState.get().setAppliedFilters(newFilter);
    }

    private boolean isValidInput(String text) {
        return !text.isEmpty();
    }
}
