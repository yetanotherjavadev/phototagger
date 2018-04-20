package com.paka.tagger.state.matchers;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.state.AppState;
import com.paka.tagger.state.TagFilterMode;
import com.paka.tagger.state.filters.TagFilter;
import java.util.Set;

public class MatchingUtils {

    private MatchingUtils() {}

    //TODO convert to java8 map matcher
    public static boolean isMatchingAnyTag(TagFilter filter, TreeEntity entity) {
        Set<Tag> tags = entity.getTagsAssigned();
        if (tags == null) return false;
        Set<Tag> filterTags = filter.getSelectedTags();
        for (Tag filterTag : filterTags) {
            if (tags.contains(filterTag)) return true;
        }

        return false;
    }

    public static boolean isMatchingAllTags(TagFilter filter, TreeEntity entity) {
        Set<Tag> entityTags = entity.getTagsAssigned();
        Set<Tag> filterTags = filter.getSelectedTags();

        return containsAll(entityTags, filterTags);
    }

    public static boolean isMatching(TagFilter filter, TreeEntity entity) {
        if (filter == null || filter.getSelectedTags().isEmpty()) return true;
        TagFilterMode tagFilterMode = AppState.get().getAppSettingsProperty().getValue().getTaggingModeProperty().getValue();
        if (tagFilterMode == TagFilterMode.MATCH_ANY) {
            return MatchingUtils.isMatchingAnyTag(filter, entity); //should I consider using parent value here too?
        } else {
            return MatchingUtils.isMatchingAllTags(filter, entity);
        }
        //add partial match feature (with assignable "matching weights")
    }

    private static boolean equalTagSets(Set<Tag> first, Set<Tag> second) {
        if (first == null && second == null) return true;
        if (first == null || second == null) return false;
        return first.containsAll(second) && second.containsAll(first);
    }

    private static boolean containsAll(Set<Tag> container, Set<Tag> contained) {
       return container.containsAll(contained);
    }
}