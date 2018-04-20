package com.paka.tagger.persistence.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.zip.CRC32;

public class CRCUtils {

    private CRCUtils() {}

    public static long getCRC32Checksum(String filepath) throws IOException {
        InputStream inputStream = new BufferedInputStream(new FileInputStream(filepath));
        CRC32 crc = new CRC32();
        int cnt;
        while ((cnt = inputStream.read()) != -1) {
            crc.update(cnt);
        }
        return crc.getValue();
    }

    public static long getCRC32Checksum(Path path) throws IOException {
        return getCRC32Checksum(path.toString());
    }
}

