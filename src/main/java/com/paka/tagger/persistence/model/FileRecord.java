package com.paka.tagger.persistence.model;

import com.paka.tagger.common.model.Tag;
import com.paka.tagger.model.TreeEntity;
import com.paka.tagger.persistence.utils.CRCUtils;
import java.io.IOException;
import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * File record is used for describing unique files (images)
 *
 * TODO update javadoc
 */
@Getter
@Setter
@NoArgsConstructor
public class FileRecord implements Serializable {

    private long crc32;
    private String path;
    private Set<Tag> tags;

    public FileRecord(String path) {
        this.path = path;
        try {
            this.crc32 = CRCUtils.getCRC32Checksum(path);
        } catch (IOException e) {
            System.out.println("Something is wrong with FileRecord creation: " + e);
        }
    }

    public FileRecord(TreeEntity entity) {
        this(entity.getPathItem().getFullPath().toString());
    }

    public static FileRecord ofEntity(TreeEntity entity) {
        FileRecord fr = new FileRecord(entity.getPathItem().getFullPath().toString());
        fr.setTags(entity.getTagsAssigned());
        return fr;
    }
}