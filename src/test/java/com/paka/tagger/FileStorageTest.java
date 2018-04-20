package com.paka.tagger;

import com.paka.tagger.persistence.StorageModelProvider;
import com.paka.tagger.persistence.model.DataStorageModel;
import com.paka.tagger.persistence.model.FileRecord;
import org.junit.Test;

public class FileStorageTest {

    private static final String TEST_FILE_PATH = "d:\\!temp\\level0_1\\level1_1\\level2_1\\level3_1\\crop.jpg";

    @Test
    public void testFilesFounder() {
        DataStorageModel dsm = StorageModelProvider.getStorage();

        FileRecord fr = new FileRecord(TEST_FILE_PATH);

        long crc = fr.getCrc32();
        System.out.println("New file crc = " + crc);

        dsm.writeRecord(fr);
        dsm.flush();

        dsm.readAllRecords();
        FileRecord newRead = dsm.readRecord(crc);

        System.out.println("File read successfully!" + newRead);
    }
}
