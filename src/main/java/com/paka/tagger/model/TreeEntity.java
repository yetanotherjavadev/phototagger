package com.paka.tagger.model;

import com.paka.tagger.common.model.ImageMetadata;
import com.paka.tagger.common.model.Tag;
import com.paka.tagger.persistence.StorageModelProvider;
import com.paka.tagger.persistence.model.DataStorageModel;
import com.paka.tagger.persistence.model.FileRecord;
import com.paka.tagger.persistence.utils.CRCUtils;
import com.paka.tagger.utils.FileUtils;
import com.paka.tagger.utils.ImageUtils;
import com.paka.tagger.widgets.filebrowser.items.PathItem;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class TreeEntity { //TODO: link TreeEntitiy to FileRecord based on some property

    private PathItem pathItem;
    private long crc32;
    private ImageFormat format;
    private ImageMetadata metadata;
    private Set<Tag> tagsAssigned;
    private boolean valid;
    private boolean isDirectory;

    public TreeEntity(Path path) {
        this.pathItem = new PathItem(path);
        this.isDirectory = path.toFile().isDirectory();
        if (!isDirectory) {
            try {
                this.crc32 = CRCUtils.getCRC32Checksum(path);
                this.metadata = ImageUtils.getImageMetadata(path);
                this.valid = true; // the item is "valid" if it has metadata and it is "supported"
            } catch (IOException e) {
                this.valid = false;
                //TODO: print stack trace
            }
            init();
            persist();
        }
    }

    private void init() {
        String fileExt = FileUtils.getFileExt(pathItem.getFullPath().toString());
        this.format = ImageFormat.getFormat(fileExt);
    }

    private void persist() {
        DataStorageModel dsm = StorageModelProvider.getStorage();
        if (dsm.hasRecord(crc32)) {
            FileRecord fr = dsm.readRecord(crc32);
            this.tagsAssigned = fr.getTags();
            if (tagsAssigned == null) tagsAssigned = new HashSet<>();
        } else {
            this.tagsAssigned = new HashSet<>();
            FileRecord fr = FileRecord.ofEntity(this);
            dsm.writeRecord(fr);
        }
    }

    public Set<Tag> getTagsAssigned() {
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