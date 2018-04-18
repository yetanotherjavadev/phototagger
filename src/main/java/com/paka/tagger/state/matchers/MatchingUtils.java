package com.paka.tagger.state.matchers;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.filters.TagFilter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MatchingUtils {

    private MatchingUtils() {}

    //TODO convert to java8 map matcher
    public static boolean isMatchingAnyTag(TagFilter filter, TreeEntity entity) {
        if (filter == null || filter.getSelectedTags().isEmpty()) return true;
        Set<Tag> tags = entity.getTagsAssigned();

        Set<Tag> filterTags = filter.getSelectedTags();
        for (Tag filterTag : filterTags) {
            if (tags.contains(filterTag)) return true;
        }

        return false;
    }

    public static boolean isMatchingAllTags(TagFilter filter, TreeEntity entity) {
        if (filter == null || filter.getSelectedTags().isEmpty()) return true;
        Set<Tag> tags = entity.getTagsAssigned();
        Set<Tag> filterTags = filter.getSelectedTags();

        return equalTagSets(tags, filterTags);
    }

    private static boolean equalTagSets(Set<Tag> first, Set<Tag> second) {
        List<Tag> list1 = new ArrayList<>(first);
        List<Tag> list2 = new ArrayList<>(second);

        Comparator<Tag> cmp = Comparator.comparing(Tag::getText);

        list1.sort(cmp);
        list2.sort(cmp);

        return Objects.deepEquals(new ArrayList<>(first), new ArrayList<>(second));
    }
}
