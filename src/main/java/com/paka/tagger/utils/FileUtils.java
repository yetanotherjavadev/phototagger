package com.paka.tagger.utils;

import java.nio.file.Path;

public class FileUtils {

    private FileUtils() {
    }

    public static String getFileExt(String filename) {
        String ext = ".";
        int p = filename.lastIndexOf('.');
        if (p >= 0) {
            ext = filename.substring(p + 1);
        }
        return ext.toLowerCase();
    }

    public static String getFileExt(Path path) {
        return getFileExt(path.toString());
    }

    public static String getFileName(Path file) {
        String fullName = file.toString();
        int i = fullName.lastIndexOf("\\");
        if ( i > 0) {
            return fullName.substring(i + 1);
        }

        return fullName;
    }
}
