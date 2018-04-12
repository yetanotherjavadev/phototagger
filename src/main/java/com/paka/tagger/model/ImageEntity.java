package com.paka.tagger.model;

import com.paka.tagger.widgets.tagspanel.Tag;
import java.nio.file.Path;
import java.util.List;

public class ImageEntity {

    private Path pathToImage;
    private ImageFormat format;
    private List<Tag> tagsAssigned;
    boolean processed;

    public ImageEntity(Path path) {
        this.pathToImage = path;
    }
}
