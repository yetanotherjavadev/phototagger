package com.paka.tagger.config;

import com.paka.tagger.utils.FileUtils;
import java.io.File;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class MainAppConfig {

    public static final Set<String> supportedExtensions = new HashSet<>();

    static {
        supportedExtensions.add("BMP");
        supportedExtensions.add("JPG");
        supportedExtensions.add("PNG");
    }

    public static boolean isExtSupported(String ext) {
        return supportedExtensions.contains(ext);
    }

    public static boolean isPathSupported(Path path) {
        String ext = FileUtils.getFileExt(path.toString()).toUpperCase();
        return isExtSupported(ext);
    }
}
