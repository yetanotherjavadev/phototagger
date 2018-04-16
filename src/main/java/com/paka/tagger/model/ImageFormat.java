package com.paka.tagger.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFormat { //TODO: add specific file info
    JPG("JPG"),
    JPEG("JPEG"),
    TIFF("TIFF"),
    BMP("BMP"),
    PNG("PNG");

    private String extension;

    public static ImageFormat getFormat(String extension) {
        return Arrays.stream(values()).filter((format) -> format.getExtension().equalsIgnoreCase(extension))
                .findFirst().orElse(null);
    }
}
