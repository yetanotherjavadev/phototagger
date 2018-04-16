package com.paka.tagger.config;

import com.paka.tagger.model.ImageFormat;
import com.paka.tagger.utils.FileUtils;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MainAppConfig {

    public static final Set<ImageFormat> supportedExtensions = new HashSet<>();

    static {
        supportedExtensions.add(ImageFormat.BMP);
        supportedExtensions.add(ImageFormat.JPG);
        supportedExtensions.add(ImageFormat.JPEG);
        supportedExtensions.add(ImageFormat.PNG);
    }

    public static boolean isExtSupported(String ext) {
        Optional contains = supportedExtensions.stream().filter((value) -> value.getExtension()
                .equalsIgnoreCase(ext)).findFirst();
        return contains.isPresent();
    }

    public static boolean isPathSupported(Path path) {
        String ext = FileUtils.getFileExt(path.toString()).toUpperCase();
        return isExtSupported(ext);
    }
}
