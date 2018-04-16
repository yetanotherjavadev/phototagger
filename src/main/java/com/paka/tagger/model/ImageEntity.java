package com.paka.tagger.model;

import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import com.paka.tagger.widgets.tagspanel.Tag;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ImageEntity {

    private PathItem pathItem;
    private ImageFormat format;
    private List<Tag> tagsAssigned;
    boolean processed;

    public ImageEntity(Path path) {
        this.pathItem = new PathItem(path);
        this.processed = false;
        init();
    }

    private void init() {
        String fileExt = FileUtils.getFileExt(pathItem.getFullPath().toString());
        ImageFormat format = ImageFormat.getFormat(fileExt);
        if (format == null) throw new UnsupportedOperationException("Image format not supported: " + fileExt);
        this.format = format;
    }

    public List<Tag> getTagsAssigned() {
        if (tagsAssigned == null) {
            tagsAssigned = new ArrayList<>();
        }
        return tagsAssigned;
    }

    public void addTag(Tag tag) {
        getTagsAssigned().add(tag);
    }

    public boolean removeTag(Tag tag) {
        return getTagsAssigned().remove(tag);
    }

    public void update() {
        this.processed = true;
    }

    public void clearTags() {
        getTagsAssigned().clear();
    }

    public boolean hasTagOn(Tag tag) {
        return getTagsAssigned().contains(tag);
    }
}