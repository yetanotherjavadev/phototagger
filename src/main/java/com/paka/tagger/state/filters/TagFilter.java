package com.paka.tagger.state.filters;

import com.paka.tagger.common.model.Tag;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class TagFilter implements Filter {

    private Set<Tag> selectedTags;

    public TagFilter() {
        this.selectedTags = new HashSet<>();
    }

    public TagFilter(Set<Tag> tags) {
        this.selectedTags = tags;
    }

    @Override
    public FilterType getType() {
        return FilterType.TAG;
    }
}
