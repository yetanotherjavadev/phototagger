package com.paka.tagger.common.model;

import java.util.Set;
import java.util.WeakHashMap;

/**
 * Tag cloud is a model that reflects the most frequently used tags and processed them
 */
public class TagCloud {

    private Set<Tag> allTags;
    private WeakHashMap<String, Tag> mostUsedTags;



}
