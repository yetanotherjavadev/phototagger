package com.paka.tagger.persistence.model;

/**
 * will be implemented as file storage and database storage
 */
public interface DataStorageModel {

    FileRecord readRecord(long crcKey);

    void writeRecord(FileRecord record);

    void writeRecord(FileRecord record, boolean instaFlush);

    void readAllRecords();

    void flush();

    boolean hasRecord(long crc32);
}
