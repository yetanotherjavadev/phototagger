package com.paka.tagger.persistence;

import com.paka.tagger.persistence.model.DataStorageModel;
import com.paka.tagger.persistence.model.FileDataStorageModel;

public class StorageModelProvider {

    public static DataStorageModel getStorage() {
        return FileDataStorageModel.get(); //TODO: change to database implementation when ready
    }
}
