package com.paka.tagger.persistence.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class FileDataStorageModel implements DataStorageModel {

    private static final String DB_FILE_NAME = "d:/tagger-storage.json"; //TODO: move to a special service place (installation folder?)

    private static FileDataStorageModel INSTANCE = new FileDataStorageModel();

    private Map<Long, FileRecord> mainStorage;

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private TypeReference<HashMap<Long, FileRecord>> mainReference = new TypeReference<HashMap<Long, FileRecord>>() {};

    private FileDataStorageModel() {}

    public static FileDataStorageModel get() {
        return INSTANCE;
    }

    @Override
    public FileRecord readRecord(long crcKey) {
        return getMainStorage().get(crcKey);
    }

    @Override
    public void writeRecord(FileRecord record) {
        writeRecord(record, false);
    }

    @Override
    public void writeRecord(FileRecord record, boolean instaFlush) {
        try {
            getMainStorage().put(record.getCrc32(), record);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (instaFlush) {
            flush();
        }
    }

    @Override
    public void readAllRecords() {
        try {
            File f = new File(DB_FILE_NAME);
            FileInputStream fis = new FileInputStream(f);
            mainStorage = objectMapper.readValue(fis, mainReference);
            if (mainStorage == null) {
                mainStorage = new HashMap<>();
            }
        } catch (Exception e) {
            System.out.println("Failed to get data from file: " + e);
        }
    }

    @Override
    public void flush() {
        try {
            File file = new File(DB_FILE_NAME);
            FileOutputStream fos = new FileOutputStream(file);

            objectMapper.writeValue(fos, mainStorage);
            fos.close();
        } catch (Exception e) {
            System.out.println("Failed: " + e);
        }
    }

    private Map<Long, FileRecord> getMainStorage() {
        if (mainStorage == null) {
            readAllRecords();
        }
        return mainStorage;
    }

    @Override
    public boolean hasRecord(long crc32) {
        return getMainStorage().containsKey(crc32);
    }
}

