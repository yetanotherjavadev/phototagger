package com.paka.tagger.utils;

import com.paka.tagger.common.model.ImageMetadata;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class ImageUtils {

    private ImageUtils() {}

    public static ImageMetadata getImageMetadata(Path path) throws IOException {
        ImageMetadata md = new ImageMetadata();
        BasicFileAttributes view = Files.getFileAttributeView(path, BasicFileAttributeView.class).readAttributes();
        md.setDate(new Date(view.creationTime().toMillis()));
        return md;
    }
}
