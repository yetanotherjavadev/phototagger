package com.paka.tagger.files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilesManager {

    private static final String SEARCH_PATH = "src/main/resources/test";

    public List<String> getFiles() {
        List<String> result = new ArrayList<>();

        File f = new File(SEARCH_PATH);

        if (f.exists()) {
            result.addAll(getFilesInternal(f));
        }
        return result;
    }

    private List<String> getFilesInternal(File f) {
        List<String> r = new ArrayList<>();
        if (f.isDirectory()) {
            File[] listFiles = f.listFiles();
            if (listFiles != null) {
                for (File aFile : listFiles) {
                    r.addAll(getFilesInternal(aFile));
                }
            }
        } else r.add(f.getName());
        return r;
    }

}
