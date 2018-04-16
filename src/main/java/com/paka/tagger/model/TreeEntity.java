package com.paka.tagger.model;

import com.paka.tagger.common.model.ImageMetadata;
import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.utils.ImageUtils;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import com.paka.tagger.widgets.tagspanel.Tag;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class TreeEntity {

    private PathItem pathItem;
    private ImageFormat format;
    private ImageMetadata metadata;
    private List<Tag> tagsAssigned;
    private boolean valid;

    public TreeEntity(Path path) {
        this.pathItem = new PathItem(path);
        try {
            this.metadata = ImageUtils.getImageMetadata(path);
            this.valid = true;
        } catch (IOException e) {
            this.valid = false;
            //TODO: print stack trace lol
        }
        init();
    }

    private void init() {
        String fileExt = FileUtils.getFileExt(pathItem.getFullPath().toString());
        this.format = ImageFormat.getFormat(fileExt);
    }

    public List<Tag> getTagsAssigned() {
        if (tagsAssigned == null) {
            tagsAssigned = new ArrayList<>();
        }
        return tagsAssigned;
    }

    public ImageFormat getFormat() {
        return format;
    }

    public void addTag(Tag tag) {
        getTagsAssigned().add(tag);
    }

    public boolean removeTag(Tag tag) {
        return getTagsAssigned().remove(tag);
    }

    public void update() {
        this.valid = true;
    }

    public void clearTags() {
        getTagsAssigned().clear();
    }

    public boolean hasTagOn(Tag tag) {
        return getTagsAssigned().contains(tag);
    }

    public boolean hasTagsAssigned() {
        return tagsAssigned == null || tagsAssigned.size() == 0;
    }


    @Override //TODO: fix it by changing TreeView Cell rendering
    public String toString() {
        return pathItem.getDisplayPath();
    }
}