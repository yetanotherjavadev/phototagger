package com.paka.tagger.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageFormat {
    JPG("JPG"),
    JPEG("JPEG"),
    TIFF("TIFF"),
    BMP("BMP"),
    PNG("PNG");

    private String extension;
}
