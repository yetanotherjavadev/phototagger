package com.paka.tagger.state.filters;

import com.paka.tagger.common.model.Tag;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class TagFilter implements Filter {

    private List<Tag> selectedTags;

    public TagFilter() {
        this.selectedTags = new ArrayList<>();
    }

    public TagFilter(List<Tag> tags) {
        this.selectedTags = tags;
    }

    @Override
    public boolean apply() {
        return false;
    }
}
