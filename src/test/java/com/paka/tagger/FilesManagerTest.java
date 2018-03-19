package com.paka.tagger;

import com.paka.tagger.files.FilesManager;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FilesManagerTest {

    @Test
    public void testFilesFounder() {
        FilesManager fm = new FilesManager();

        List<String> files = fm.getFiles();

        Assert.assertEquals(10, files.size());
    }
}
