package com.paka.tagger.model;

import com.paka.tagger.common.model.ImageMetadata;
import com.paka.tagger.common.model.Tag;
import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.utils.ImageUtils;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
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
    private boolean isDirectory;

    public TreeEntity(Path path) {
        this.pathItem = new PathItem(path);
        this.isDirectory = path.toFile().isDirectory();
        try {
            this.metadata = ImageUtils.getImageMetadata(path);
            this.valid = true; // the item is "valid" if it has metadata and it is "supported"
        } catch (IOException e) {
            this.valid = false;
            //TODO: print stack trace
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

    public void clearTags() {
        getTagsAssigned().clear();
    }

    public boolean hasTagOn(Tag tag) {
        return getTagsAssigned().contains(tag);
    }

    public boolean hasTagsAssigned() {
        return tagsAssigned != null && tagsAssigned.size() > 0;
    }

    @Override //TODO: fix it by changing TreeView Cell rendering
    public String toString() {
        return pathItem.getDisplayPath();
    }
}