package com.paka.tagger.model;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFormat { //TODO: add specific file info
    JPG("JPG"),
    JPEG("JPEG"), //TODO merge into a single one
    GIF("GIF"),
    BMP("BMP"),
    PNG("PNG"),
    NOT_SUPPORTED("Not supported");

    private String extension;

    public static ImageFormat getFormat(String extension) {
        return Arrays.stream(values()).filter((format) -> format.getExtension().equalsIgnoreCase(extension))
                .findFirst().orElse(NOT_SUPPORTED);
    }
}
