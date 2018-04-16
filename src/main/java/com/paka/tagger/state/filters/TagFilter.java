package com.paka.tagger.state.filters;

import com.paka.tagger.widgets.tagspanel.Tag;
import java.util.ArrayList;
import java.util.List;

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
