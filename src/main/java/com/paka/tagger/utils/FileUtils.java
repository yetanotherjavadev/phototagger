package com.paka.tagger.utils;

public class FileUtils {

    private FileUtils() {
    }

    public static String getFileExt(String filename) {
        String ext = ".";
        int p = filename.lastIndexOf('.');
        if (p >= 0) {
            ext = filename.substring(p);
        }
        return ext.toLowerCase();
    }

}
